using Core.Signals;
using Example.Inventory.Configs;

namespace Example.GameElements.CollisionSystem.CollisionRules.InventoryRules
{
    public class Pistol01Rule : WeaponPickupRule
    {
        protected override EWeaponType WeaponType => EWeaponType.Pistol01;

        public Pistol01Rule(ISignalSender signalSender) : base(signalSender)
        {
        }
    }
}