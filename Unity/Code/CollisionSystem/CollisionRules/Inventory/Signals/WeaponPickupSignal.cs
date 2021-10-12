using Core.Signals.Domain;
using Example.Inventory.Configs;

namespace Example.GameElements.CollisionSystem.CollisionRules.Inventory.Signals
{
    public class WeaponPickupSignal : Signal
    {
        public EWeaponType WeaponType { get; set; }

        public override void Clear()
        {
            WeaponType = EWeaponType.None;
        }
    }
}