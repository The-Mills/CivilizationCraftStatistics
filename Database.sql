CREATE DATABASE civilizationcraft_statistics;
USE civilizationcraft_statistics;
CREATE TABLE PlayerStats (
    UUID CHAR(36) NOT NULL,
    STONEMined INT UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (UUID)
);
