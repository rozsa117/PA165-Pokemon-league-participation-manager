package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

/**
 * DTO class for autheticate Trainer.
 *
 * @author Jiří Medveď 38451
 */
public class TrainerAuthenticateDTO
{
    private Long trainerId;
    private String password;

    public Long getUserId()
    {
        return trainerId;
    }

    public void setUserId(Long userId)
    {
        this.trainerId = userId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
