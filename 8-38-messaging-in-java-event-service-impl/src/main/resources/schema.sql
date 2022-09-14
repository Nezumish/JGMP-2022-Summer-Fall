CREATE TABLE `event`(
    `id` SERIAL PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `place` VARCHAR(255) NOT NULL,
    `speaker` VARCHAR(255) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
    `date_time` TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);