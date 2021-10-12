using Example.Framework;
using Example.GameElements.CollisionSystem.CollisionRules;
using Example.GameElements.CollisionSystem.Configs;
using UnityEngine;
using Zenject;
using IDisposable = System.IDisposable;

namespace Example.GameElements.CollisionSystem
{
    public interface ICollisionSystem<T> : IInitializable , IDisposable where T : BaseRule
    {
        void ApproveCollision(ECollisionType collisionType, GameObject otherGameObject);
    }
}