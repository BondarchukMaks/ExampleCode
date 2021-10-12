using Core.Signals;
using Example.Inventory.Configs;

namespace Example.GameElements.CollisionSystem.CollisionRules.InventoryRules
{
    public class Pistol02Rule : WeaponPickupRule
    {    
        protected override EWeaponType WeaponType => EWeaponType.Pistol02;

        public Pistol02Rule(ISignalSender signalSender) : base(signalSender)
        {
        }
    }
}