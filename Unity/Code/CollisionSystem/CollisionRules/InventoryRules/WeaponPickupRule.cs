using Assets.Scripts.Core.Analytics;
using Core.Signals;
using Example.Inventory.Configs;
using System.Collections.Generic;
using Assets.Scripts.Core.Analytics.Events;
using Example.GameElements.CollisionSystem.CollisionRules.Inventory.Signals;

namespace Example.GameElements.CollisionSystem.CollisionRules.InventoryRules
{
    public abstract class WeaponPickupRule : BaseInventoryRule
    {
        protected abstract EWeaponType WeaponType { get; }
        
        private ISignalSender _signalSender;
        private readonly AnalyticsAgent _analytics;

        protected WeaponPickupRule(ISignalSender signalSender)
        {
            _signalSender = signalSender;
            _analytics = new AnalyticsAgent(_signalSender);
        }

        public override void SetRule()
        {
            var signal = _signalSender.GetSignal<WeaponPickupSignal>();
            signal.WeaponType = WeaponType;
            _signalSender.Send(signal);
            
            _analytics.Send(new WeaponPickedUp(WeaponType));
        }
    }
}