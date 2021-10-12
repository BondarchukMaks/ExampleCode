using System.Collections.Generic;
using Example.GameElements.CollisionSystem.CollisionRules.BoostRules;
using Example.GameElements.CollisionSystem.Configs;

namespace Example.GameElements.CollisionSystem.CollisionSystems
{
    public class BoostCollisionSystem : BaseCollisionSystem<BaseBoostRule>
    {
        protected override void SetupRules()
        {
            PoolInfo = new Dictionary<ECollisionType, BaseBoostRule>()
            {
                {ECollisionType.SpeedBoost, new SpeedRule(SignalSender)},
                {ECollisionType.JumpBoost, new JumpRule(SignalSender)},
                {ECollisionType.HealthBoost,new HealthRule(SignalSender)}
            };
        }
    }
}