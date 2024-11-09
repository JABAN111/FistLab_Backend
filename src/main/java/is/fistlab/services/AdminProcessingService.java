package is.fistlab.services;

import is.fistlab.database.entities.PotentialAdmin;
import is.fistlab.database.entities.User;

import java.util.List;

public interface AdminProcessingService {

    /**
     * Метод, который проверяет, если есть хотя бы один админ в системе, то сохраняет запрос в очередь на обработку
     * Если админа нет, то присваивает первому пользователю роль админа
     */
    boolean isAnyAdminExist();

    List<PotentialAdmin> getAllPotentialAdmins();

    void addUserToWaitingList(User user);

    void removeUserFromWaitingList(User user);

    void giveAdminRoleToUser(User user);
}