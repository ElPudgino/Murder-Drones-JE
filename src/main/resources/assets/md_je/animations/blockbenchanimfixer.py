data = []
fname = "tribal.animation.json"
with open(fname) as f:
    for i in f:
        data.append(i)

i = 0
for i in range(len(data)):
    if i >= len(data):
        break
    line = data[i]
    if "pre" in line:
        data.pop(i)
        data.pop(i)
        data.pop(i)

i = 0
for i in range(len(data)):
    if i >= len(data):
        break
    line = data[i]
    if "post" in line:
        ln = data[i+1]
        ln = ln[1:-1] + ",\n"
        data[i] = ln
        data.pop(i+1)
        data.pop(i+1)
        

with open(fname, "w") as f:
    for line in data:
        f.write(line)
