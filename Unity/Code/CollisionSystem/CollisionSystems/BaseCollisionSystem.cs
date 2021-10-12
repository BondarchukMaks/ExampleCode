using System.Collections.Generic;
using Example.Framework;
using Example.Framework.Core.Signals;
using Example.GameElements.CollisionSystem.CollisionRules;
using Example.GameElements.CollisionSystem.Configs;
using UnityEngine;

namespace Example.GameElements.CollisionSystem.CollisionSystems
{
    public abstract class BaseCollisionSystem<T> : ICollisionSystem<T> where T : BaseRule , IInitializable , IDisposable
    {
        [Inject] protected IGameStatus GameStatus;
        [Inject] protected ISignalSender SignalSender;
        
        protected Dictionary<ECollisionType, T> PoolInfo;
        
        protected abstract void SetupRules();
        
        public void Initialize()
        {
            SetupRules();
        }

        public void Dispose()
        {
            PoolInfo.Clear();
        }

        public void ApproveCollision(ECollisionType collisionType, GameObject otherGameObject)
        {
            if (PoolInfo.TryGetValue(collisionType, out var rule))
            {
                rule.SetRule();
            }
            else
            {
                 Debug.Log($"Can't find collision rule {collisionType}");
            }
        }
    }
}