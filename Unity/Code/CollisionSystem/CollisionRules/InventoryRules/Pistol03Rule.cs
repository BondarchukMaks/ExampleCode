using Core.Signals;
using Example.Inventory.Configs;

namespace Example.GameElements.CollisionSystem.CollisionRules.InventoryRules
{
    public class Pistol03Rule : WeaponPickupRule
    {
        protected override EWeaponType WeaponType => EWeaponType.Pistol03;

        public Pistol03Rule(ISignalSender signalSender) : base(signalSender)
        {
        }
    }
}