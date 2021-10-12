using System.Collections.Generic;
using Example.GameElements.CollisionSystem.CollisionRules.InventoryRules;
using Example.GameElements.CollisionSystem.Configs;

namespace Example.GameElements.CollisionSystem.CollisionSystems
{
    public class InventoryCollisionSystem : BaseCollisionSystem<BaseInventoryRule>
    {
        protected override void SetupRules()
        {
            PoolInfo = new Dictionary<ECollisionType, BaseInventoryRule>()
            {
                {ECollisionType.Gun, new GunRule(SignalSender)},
                {ECollisionType.Pistol01, new Pistol01Rule(SignalSender)},
                {ECollisionType.Pistol02, new Pistol02Rule(SignalSender)},
                {ECollisionType.Pistol03, new Pistol03Rule(SignalSender)},
                {ECollisionType.Pistol04, new Pistol04Rule(SignalSender)}
            };
        }
    }
}