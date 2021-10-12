using Core.Signals;
using Example.Inventory.Configs;

namespace Example.GameElements.CollisionSystem.CollisionRules.InventoryRules
{
    public class Pistol04Rule : WeaponPickupRule
    {
        protected override EWeaponType WeaponType => EWeaponType.Pistol04;

        public Pistol04Rule(ISignalSender signalSender) : base(signalSender)
        {
        }
    }
}