DELIMITER //

CREATE TRIGGER after_transaction_insert
    AFTER INSERT ON saving
    FOR EACH ROW
BEGIN
    INSERT INTO messages (userid, amount, message)
    VALUES (NEW.userid, NEW.amount, CONCAT('Saving of ', NEW.amount, ' completed'));
END;
//

CREATE TRIGGER after_withdraw_insert
    AFTER INSERT ON withdraw
    FOR EACH ROW
BEGIN
    INSERT INTO messages (userid, amount, message)
    VALUES (NEW.userid, NEW.amount, CONCAT('Withdrawal of ', NEW.amount, ' completed'));
END;
//

CREATE TRIGGER after_transfer_insert
    AFTER INSERT ON transfer
    FOR EACH ROW
BEGIN
    INSERT INTO messages (userid, amount, message)
    VALUES (NEW.userid, NEW.amount, CONCAT('Transfer of ', NEW.amount, ' completed'));
END;
//

DELIMITER ;