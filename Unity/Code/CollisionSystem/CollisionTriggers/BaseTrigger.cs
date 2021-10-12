using UnityEngine;

namespace Example.GameElements.CollisionSystem.CollisionTriggers
{
    public abstract class BaseTrigger : BaseGameElement
    {
        public abstract void OnCollisionGameObject(Collider collider,
            GameObject otherGameObject);
    }
}