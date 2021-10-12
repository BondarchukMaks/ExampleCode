using System.Collections.Generic;
using Example.GameElements.CollisionSystem.CollisionTriggers;
using UnityEngine;

namespace Example.GameElements.CollisionSystem
{
    public class CollisionContainerTrigger : BaseGameElement
    {
        [SerializeField]
        private List<BaseTrigger> _collisionTriggers = new List<BaseTrigger>();
        
        private void OnTriggerEnter(Collider other)
        {
            _collisionTriggers.ForEach(item => item.OnCollisionGameObject(other, other.gameObject));
        }
    }
}