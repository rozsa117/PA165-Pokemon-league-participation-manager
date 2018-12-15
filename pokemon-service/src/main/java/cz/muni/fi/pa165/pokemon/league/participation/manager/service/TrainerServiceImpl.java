package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.TrainerDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.DAOExceptionWrapper;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
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
    private GymDAO gymDao;

    @Override
    public boolean changePassword(Trainer trainer, String oldPassword, String newPassword) {
        if (!authenticate(trainer, oldPassword)) {
            return false;
        }
        trainer.setPasswordHash(createHash(newPassword));
        daoUpdateTrainer(trainer);
        return true;
    }

    @Override
    public Trainer createTrainer(Trainer trainer, String password) throws NoAdministratorException {
        //if adding trainer that is not an admin check if there is at least one already
        if (!trainer.isAdmin() && daoGetAdminCount() == 0) {
            throw new NoAdministratorException("There must be at least one admin");
        }

        trainer.setPasswordHash(createHash(password));
        DAOExceptionWrapper.withoutResult(() -> trainerDao.createTrainer(trainer), "createTrainerFailed");
        return trainer;
    }

    @Override
    public void setAdmin(Trainer trainer, boolean admin) throws NoAdministratorException {
        // if admin flag is changed from true to false check if at least one admin remains
        if (!admin && trainer.isAdmin()
                && daoGetAdminCount() == 1) {
            throw new NoAdministratorException("There must be at least one admin");
        }
        trainer.setAdmin(admin);
        daoUpdateTrainer(trainer);
    }

    @Override
    public void renameTrainer(Trainer trainer, String newName, String newSurname) {
        trainer.setName(newName);
        trainer.setSurname(newSurname);
        daoUpdateTrainer(trainer);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return DAOExceptionWrapper.withResult(() -> trainerDao.getAllTrainers(), "getAllTrainers failed");
    }

    @Override
    public Trainer getTrainerWithId(Long id) {
        return DAOExceptionWrapper.withResult(() -> trainerDao.findTrainerById(id), "findTrainerById failed");
    }

    @Override
    public boolean authenticate(Trainer trainer, String password) {
        return validatePassword(password, trainer.getPasswordHash());
    }

    @Override
    public boolean isGymLeader(Trainer trainer) {
        return DAOExceptionWrapper.withResult(() -> gymDao.getAllGyms(), "getAllGyms failed")
                .stream().anyMatch((gym) -> (gym.getGymLeader().equals(trainer)));
    }    
    
    @Override
    public Trainer findTrainerByUsername(String username) {
        return DAOExceptionWrapper.withResult(() -> trainerDao.findTrainerByUsername(username), "findTrainerByUsername failed");
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
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
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

    private long daoGetAdminCount() {
        return DAOExceptionWrapper.withResult(() -> trainerDao.getAdminCount(), "getAdminCount failed");
    }

    private void daoUpdateTrainer(Trainer trainer) {
        DAOExceptionWrapper.withoutResult(() -> trainerDao.updateTrainer(trainer), "updateTrainer failed");
    }

}
