using Assets.Scripts.Core.Analytics;
using Core.Signals;
using Example.Status;
using MoreMountains.NiceVibrations;
using ProjectScene.GlobalInfo;
using System.Collections.Generic;
using Assets.Scripts.Core.Analytics.Events;
using Example.Framework.Core.Signals;
using Example.GameElements.CollisionSystem.CollisionRules.GameplayRules.Signals;

namespace Example.GameElements.CollisionSystem.CollisionRules.GameplayRules
{
    public class WinRule : BaseGameplayRule
    {
        private IGlobalInfoSystem _info;
        private IGameStatus _gameStatus;
        private ISignalSender _signalSender;
        
        private readonly AnalyticsAgent _analytics;

        public WinRule(IGlobalInfoSystem info, IGameStatus gameStatus,
            ISignalSender signalSender)
        {
            _info = info;
            _gameStatus = gameStatus;
            _signalSender = signalSender;
            _analytics = new AnalyticsAgent(_signalSender);
        }

        public override void SetRule()
        {
            _info.GameProgress.Increment();

            _signalSender.SendEmpty<WinSignal>();
            
            SendWinEvent(_info.GameProgress.CurrentValue, _info.CashCounter.CurrentValue);
            
            _gameStatus.SetStatus(EStatus.Win); 
            MMVibrationManager.SetHapticsActive(true);
            MMVibrationManager.Haptic(HapticTypes.Success);
        }

        private void SendWinEvent(int level, int cashBalance)
        {
            _analytics.Send(new LevelUp(level, cashBalance));
        }
    }
}