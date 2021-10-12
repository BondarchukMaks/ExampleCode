using System.Collections.Generic;
using Core.PopUp;
using Core.Signals;
using Core.Utils.MonoCoroutine;
using Example.GameElements.CollisionSystem.CollisionRules.GameplayRules;
using Example.GameElements.CollisionSystem.Configs;
using Example.Info;
using ProjectScene.GlobalInfo;
using Zenject;

namespace Example.GameElements.CollisionSystem.CollisionSystems
{
    public class GameplayCollisionSystem : BaseCollisionSystem<BaseGameplayRule>
    {
        [Inject] private IGlobalInfoSystem _globalInfoSystem;
        [Inject] private IGameInfo _gameInfo;
        [Inject] private IPopupSystem _popupSystem;
        [Inject] private ISignalSender _signalSender;
        [Inject] private ISignalSubscriber _signalSubscriber;
        [Inject] private ICoroutineManager _coroutineManager;

        protected override void SetupRules()
        {
            PoolInfo = new Dictionary<ECollisionType, BaseGameplayRule>()
            {
                {ECollisionType.SmileBonus, new SmileRule(_gameInfo,_signalSender,_signalSubscriber)},
                {ECollisionType.Obstacle, new ObstacleRule(GameStatus)},
                {ECollisionType.WinPlatform, new WinRule(_globalInfoSystem, GameStatus, _signalSender)},
                {ECollisionType.Bullet, new PlayerDamageRule(_popupSystem)},
                {ECollisionType.Smile2XBoost, new Smile2XBoostRule(_signalSender,_coroutineManager)}
            };
        }
    }
}