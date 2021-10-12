using Example.Status;

namespace Example.GameElements.CollisionSystem.CollisionRules.GameplayRules
{
    public class ObstacleRule : BaseGameplayRule
    {
        private IGameStatus _info;
        
        public ObstacleRule(IGameStatus info)
        {
            _info = info;
        }

        public override void SetRule()
        {
            _info.SetStatus(EStatus.GameOver);
        }
    }
}


