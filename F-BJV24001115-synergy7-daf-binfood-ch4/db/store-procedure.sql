CREATE OR REPLACE PROCEDURE confirmOrderCompletion(
    IN orderId UUID,
    IN isCompleted BOOLEAN
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE orders
    SET completed = isCompleted
    WHERE id = orderId;
END;
$$;
