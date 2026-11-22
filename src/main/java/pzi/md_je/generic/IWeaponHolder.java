package pzi.md_je.generic;

import pzi.md_je.network.to_server.SUseWeaponPacket;

public interface IWeaponHolder 
{
    public void OnWeaponAction(int wepid, int actionType, boolean server);
}
