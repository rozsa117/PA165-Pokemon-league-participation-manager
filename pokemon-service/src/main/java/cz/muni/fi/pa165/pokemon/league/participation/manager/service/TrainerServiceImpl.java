package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.TrainerDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Service interface implementation for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    @Inject
    private TrainerDAO trainerDao;

    @Inject
    private PokemonDAO pokemonDao;

    @Inject
    private GymDAO gymDao;

    @Override
    public Trainer createTrainer(Trainer trainer, String password) {
        trainer.setPasswordHash(createHash(password));
        trainerDao.createTrainer(trainer);
        return trainer;
    }

    @Override
    public void renameTrainer(Trainer trainer, String newName, String newSurname) {
        trainer.setName(newName);
        trainer.setName(newSurname);
        trainerDao.updateTrainer(trainer);
    }

    @Override
    public void addPokemon(Trainer trainer, Pokemon pokemon) {
        trainer.addPokemon(pokemon);
        trainerDao.updateTrainer(trainer);
    }

    @Override
    public void removePokemon(Trainer trainer, Pokemon pokemon) {
        trainer.deletePokemon(pokemon);
        trainerDao.updateTrainer(trainer);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDao.getAllTrainers();
    }

    @Override
    public Trainer getTrainerWithId(Long id) {
        return trainerDao.findTrainerById(id);
    }

    @Override
    public Boolean authenticate(Trainer trainer, String password) {
        return validatePassword(password, trainer.getPasswordHash());
    }

    @Override
    public Boolean isGymLeader(Trainer trainer) {
        return (gymDao.getAllGyms().stream()
                .anyMatch((gym) -> (gym.getGymLeader().equals(trainer))));
    }

    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        if (password == null) {
            return false;
        }
        if (correctHash == null) {
            throw new IllegalArgumentException("password hash is null");
        }
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

}
