package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> sourseAccount = getById(fromId);
        Optional<Account> destinationAccount = getById(toId);
        if (sourseAccount.isPresent() && destinationAccount.isPresent()) {
            if (sourseAccount.get().amount() >= amount) {
                update(new Account(fromId,
                        sourseAccount.get().amount() - amount));
                update(new Account(toId,
                        destinationAccount.get().amount() + amount));
                return true;
            }
        }
        return false;
    }
}
