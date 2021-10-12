using Core.Signals;
using Example.Inventory.Configs;

namespace Example.GameElements.CollisionSystem.CollisionRules.InventoryRules
{
    public class GunRule : WeaponPickupRule
    {
        protected override EWeaponType WeaponType => EWeaponType.Gun;

        public GunRule(ISignalSender signalSender) : base(signalSender)
        {
        }
    }
}