using Assets.Scripts.Core.Analytics;
using Core.Signals;
using Assets.Scripts.Core.Analytics.Events;
using Example.Framework.Core.Signals;

namespace Example.GameElements.CollisionSystem.CollisionRules.BoostRules
{
    public abstract class BaseBoostRule : BaseRule
    {
        private BoostType _boostType;
        private readonly AnalyticsAgent _analytics;
        
        protected ISignalSender _signalSender;

        public BaseBoostRule(ISignalSender signalSender, BoostType boostType)
        {
            _signalSender = signalSender;
            _boostType = boostType;
            
            _analytics = new AnalyticsAgent(_signalSender);
        }

        public override void SetRule()
        {
            _analytics.Send(new BoostPickedUp(_boostType));
        }
    }
}