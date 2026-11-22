#!/usr/bin/env python3
"""
Replace lines of the form:

    some_name = new ChModelRenderer(this);

with:

    some_name = new ChModelRenderer(this, "some_name");

Usage:
    python replace_renderer.py <file> [<file> ...]
    python replace_renderer.py -i <file>          # edit in place

The script preserves leading indentation and trailing newline style.
"""

import argparse
import re
import sys


# Regex explanation:
#   ^(\s*)              -> capture leading indentation
#   ([A-Za-z_]\w*)      -> capture the variable name
#   \s*=\s*             -> the `=` with optional whitespace
#   new\s+ChModelRenderer\s*\(\s*this\s*\)\s*;
#                       -> `new ChModelRenderer(this);` with flexible whitespace
PATTERN = re.compile(
    r'^(\s*)([A-Za-z_]\w*)\s*=\s*new\s+ChModelRenderer\s*\(\s*this\s*\)\s*;\s*$'
)


def replace_line(line: str) -> str:
    """Return the transformed line if it matches, otherwise the original."""
    # Preserve trailing newline
    newline = ''
    if line.endswith('\n'):
        newline = '\n'
        line_body = line[:-1]
    else:
        line_body = line

    match = PATTERN.match(line_body)
    if not match:
        return line

    indent, name = match.group(1), match.group(2)
    return f'{indent}{name} = new ChModelRenderer(this, "{name}");{newline}'


def process_file(path: str, in_place: bool) -> bool:
    """Process a single file. Returns True if any change was made."""
    try:
        with open(path, 'r', encoding='utf-8') as f:
            original = f.readlines()
    except OSError as e:
        print(f'error: cannot read {path}: {e}', file=sys.stderr)
        return False

    updated = [replace_line(line) for line in original]

    if updated == original:
        print(f'no changes: {path}')
        return False

    if in_place:
        try:
            with open(path, 'w', encoding='utf-8') as f:
                f.writelines(updated)
        except OSError as e:
            print(f'error: cannot write {path}: {e}', file=sys.stderr)
            return False
        print(f'updated:   {path}')
    else:
        sys.stdout.writelines(updated)
    return True


def main() -> int:
    parser = argparse.ArgumentParser(
        description='Rewrite `new ChModelRenderer(this);` to include the variable name.'
    )
    parser.add_argument(
        '-i', '--in-place',
        action='store_true',
        help='edit files in place instead of printing to stdout',
    )
    parser.add_argument('files', nargs='+', help='files to process')
    args = parser.parse_args()

    if not args.in_place and len(args.files) > 1:
        parser.error('cannot print multiple files to stdout; use -i')

    changed_any = False
    for path in args.files:
        if process_file(path, args.in_place):
            changed_any = True

    return 0 if changed_any else 0


if __name__ == '__main__':
    sys.exit(main())