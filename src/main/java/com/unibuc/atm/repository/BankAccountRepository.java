package com.unibuc.atm.repository;

import com.unibuc.atm.exception.BankAccountNotFoundException;
import com.unibuc.atm.model.BankAccount;
import com.unibuc.atm.model.BankAccountType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class BankAccountRepository {

    private JdbcTemplate jdbcTemplate;

    public BankAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BankAccount createBankAccount(BankAccount bankAccount) {
        String sql = "insert into bankaccounts values (?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setString(2, bankAccount.getAccountNumber());
            preparedStatement.setDouble(3, bankAccount.getBalance());
            preparedStatement.setString(4, bankAccount.getType().toString());
            preparedStatement.setString(5, bankAccount.getCardNumber());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        bankAccount.setId(generatedKeyHolder.getKey().longValue());
        return bankAccount;
    }

    public Optional<BankAccount> getBankAccount(Long id) {
        String sql = "select * from bankaccounts ba where ba.id = ?";

        RowMapper<BankAccount> mapper = (resultSet, rowNum) ->
             new BankAccount(resultSet.getLong("id"),
                    resultSet.getString("accountNumber"),
                    resultSet.getDouble("balance"),
                    BankAccountType.valueOf(resultSet.getString("type")),
                    resultSet.getString("cardNumber"));

        List<BankAccount> bankAccounts = jdbcTemplate.query(sql, mapper, id);
        if(bankAccounts != null && !bankAccounts.isEmpty()) {
            return Optional.of(bankAccounts.get(0));
        } else {
            return Optional.empty();
        }
    }

    public void updateBankAccountBalance(long id, double amount) {
        String sql = "update bankaccounts ba set ba.balance = ba.balance + ? where ba.id = ?";
        int numberOfUpdatedBankAccounts = jdbcTemplate.update(sql, amount, id);
        if(numberOfUpdatedBankAccounts == 0) {
            throw new BankAccountNotFoundException(id);
        }
    }















}
