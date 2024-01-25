--
-- File generated with SQLiteStudio v3.4.4 on Mi Jan 24 17:05:36 2024
--
-- Text encoding used: System
--
BEGIN TRANSACTION;

-- Table: category

INSERT INTO category (
                         id,
                         category_name
                     )
                     VALUES (
                         1,
                         'Precision Tools'
                     )
ON CONFLICT(id) DO NOTHING;


INSERT INTO category (
                         id,
                         category_name
                     )
                     VALUES (
                         2,
                         'Hand Tools'
                     )
ON CONFLICT(id) DO NOTHING;

INSERT INTO category (
                         id,
                         category_name
                     )
                     VALUES (
                         3,
                         'Power Tools'
                     )
ON CONFLICT(id) DO NOTHING;

INSERT INTO category (
                         id,
                         category_name
                     )
                     VALUES (
                         4,
                         'Safety Equipment'
                     )
ON CONFLICT(id) DO NOTHING;

INSERT INTO category (
                         id,
                         category_name
                     )
                     VALUES (
                         5,
                         'Diagnostic Instruments'
                     )
ON CONFLICT(id) DO NOTHING;

INSERT INTO category (
                         id,
                         category_name
                     )
                     VALUES (
                         6,
                         'Lifting Equipment'
                     )
ON CONFLICT(id) DO NOTHING;

INSERT INTO category (
                         id,
                         category_name
                     )
                     VALUES (
                         7,
                         'Storage'
                     )
ON CONFLICT(id) DO NOTHING;

-- Table: item
INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     1,
                     'Multimeter',
                     '',
                     5,
                     6,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     2,
                     'Safety Glasses',
                     'Update',
                     4,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;


INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     3,
                     'Helmet',
                     'Update remark',
                     4,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     4,
                     'Screwdriver',
                     '',
                     2,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     5,
                     'Claw Hammer',
                     '',
                     2,
                     6,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     6,
                     'Toolboxes',
                     '',
                     6,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     7,
                     'Crane',
                     '',
                     6,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     8,
                     'LlaveUno',
                     '',
                     7,
                     7,
                     'KEYS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     9,
                     'Angle Grinder',
                     '',
                     3,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     10,
                     'Circular Saw',
                     '',
                     3,
                     5,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     11,
                     'Electric Drill',
                     '',
                     3,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     12,
                     'Hammer',
                     '',
                     2,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     13,
                     'StormBreaker',
                     '',
                     2,
                     7,
                     'TOOLS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     14,
                     'LlaveDos',
                     '',
                     7,
                     6,
                     'KEYS'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item (
                     id,
                     item_name,
                     remark,
                     category_id,
                     status_id,
                     item_group
                 )
                 VALUES (
                     15,
                     'LlaveTres',
                     '',
                     7,
                     6,
                     'KEYS'
                 )
ON CONFLICT(id) DO NOTHING;

-- Table: item_assignment
INSERT INTO item_assignment (
                                id,
                                checkin_date,
                                checkout_date,
                                giver_id,
                                item_id,
                                receiver_id
                            )
                            VALUES (
                                3,
                                1705070944390,
                                1704971310437,
                                7,
                                8,
                                7
                            )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item_assignment (
                                id,
                                checkin_date,
                                checkout_date,
                                giver_id,
                                item_id,
                                receiver_id
                            )
                            VALUES (
                                6,
                                1705073300714,
                                1704986889807,
                                1,
                                8,
                                1
                            )
ON CONFLICT(id) DO NOTHING;

INSERT INTO item_assignment (
                                id,
                                checkin_date,
                                checkout_date,
                                giver_id,
                                item_id,
                                receiver_id
                            )
                            VALUES (
                                7,
                                1704987598803,
                                1704987025694,
                                1,
                                8,
                                15
                            )
ON CONFLICT(id) DO NOTHING;


INSERT INTO item_assignment (
                                id,
                                checkin_date,
                                checkout_date,
                                giver_id,
                                item_id,
                                receiver_id
                            )
                            VALUES (
                                8,
                                1705595458140,
                                1704990635240,
                                1,
                                8,
                                1
                            )
ON CONFLICT(id) DO NOTHING;


-- Table: status
INSERT INTO status (
                       id,
                       status_name
                   )
                   VALUES (
                       1,
                       'In Inspection'
                   )
ON CONFLICT(id) DO NOTHING;

INSERT INTO status (
                       id,
                       status_name
                   )
                   VALUES (
                       2,
                       'Retired'
                   )
ON CONFLICT(id) DO NOTHING;

INSERT INTO status (
                       id,
                       status_name
                   )
                   VALUES (
                       3,
                       'Out of Service'
                   )
ON CONFLICT(id) DO NOTHING;

INSERT INTO status (
                       id,
                       status_name
                   )
                   VALUES (
                       4,
                       'Reserved'
                   )
ON CONFLICT(id) DO NOTHING;

INSERT INTO status (
                       id,
                       status_name
                   )
                   VALUES (
                       5,
                       'Under Repair'
                   )
ON CONFLICT(id) DO NOTHING;

INSERT INTO status (
                       id,
                       status_name
                   )
                   VALUES (
                       6,
                       'Check Out'
                   )
ON CONFLICT(id) DO NOTHING;

INSERT INTO status (
                       id,
                       status_name
                   )
                   VALUES (
                       7,
                       'Avaible'
                   )
ON CONFLICT(id) DO NOTHING;


-- Table: user

INSERT INTO user (
                     id,
                     user_group,
                     user_role,
                     email,
                     enable,
                     first_name,
                     last_name,
                     locked,
                     password
                 )
                 VALUES (
                     1,
                     1,
                     1,
                     'nana',
                     0,
                     'Nana',
                     'Nomi',
                     0,
                     '$2a$10$Ybb1rwbN9T/QHj/RqaOBuuBXkOUmOHC.kWdL/K/lkuvPvwSM.sGFa'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user (
                     id,
                     user_group,
                     user_role,
                     email,
                     enable,
                     first_name,
                     last_name,
                     locked,
                     password
                 )
                 VALUES (
                     7,
                     2,
                     1,
                     'leyedan@email.com',
                     0,
                     'Santiago',
                     'Queirol',
                     0,
                     '$2a$10$UGy/eIFacBS.mqhWcYKiw.NqltHteycfFFL9NkPGeH11Gct/Lzo2W'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user (
                     id,
                     user_group,
                     user_role,
                     email,
                     enable,
                     first_name,
                     last_name,
                     locked,
                     password
                 )
                 VALUES (
                     14,
                     2,
                     2,
                     'leyenda@personal.com',
                     0,
                     'Santiago',
                     'Makbu',
                     0,
                     '$2a$10$tYE.MXmqDLTGGbqKo7ZHfOPKdjcuVuPS3qkoh61Ltr9oWYEhwwNZ6'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user (
                     id,
                     user_group,
                     user_role,
                     email,
                     enable,
                     first_name,
                     last_name,
                     locked,
                     password
                 )
                 VALUES (
                     15,
                     1,
                     1,
                     'anmar@salsa.com',
                     0,
                     'Anmar',
                     'Salsa',
                     0,
                     '$2a$10$nSyt7sJGMZbnkm9X2419De6mcC2HVCSSjeBdTm4AXcd6CBDeuw7WK'
                 )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user (
                     id,
                     user_group,
                     user_role,
                     email,
                     enable,
                     first_name,
                     last_name,
                     locked,
                     password
                 )
                 VALUES (
                     17,
                     1,
                     1,
                     'cute@cute.com',
                     0,
                     'Marta',
                     'Cute',
                     0,
                     '$2a$10$elyxwKLMkJ7RqdHtd5QmOecHJNSOU/dSpye.e/2EGEq2kQemiU8rC'
                 )
ON CONFLICT(id) DO NOTHING;

-- Table: user_group

INSERT INTO user_group (
                           id,
                           group_name
                       )
                       VALUES (
                           1,
                           'Project Engineers'
                       )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_group (
                           id,
                           group_name
                       )
                       VALUES (
                           2,
                           'Assenbly Technicians'
                       )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_group (
                           id,
                           group_name
                       )
                       VALUES (
                           3,
                           'Maintenance Specialist'
                       )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_group (
                           id,
                           group_name
                       )
                       VALUES (
                           4,
                           'Maintenance Technicians'
                       )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_group (
                           id,
                           group_name
                       )
                       VALUES (
                           5,
                           'Machine Operators'
                       )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_group (
                           id,
                           group_name
                       )
                       VALUES (
                           6,
                           'Quality Inspectors'
                       )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_group (
                           id,
                           group_name
                       )
                       VALUES (
                           7,
                           'Safety Specialists'
                       )
ON CONFLICT(id) DO NOTHING;

-- Table: user_role

INSERT INTO user_role (
                          id,
                          role_name
                      )
                      VALUES (
                          1,
                          'Warehose Coordinator'
                      )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_role (
                          id,
                          role_name
                      )
                      VALUES (
                          2,
                          'Inventory Specialist'
                      )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_role (
                          id,
                          role_name
                      )
                      VALUES (
                          3,
                          'Training Specialist'
                      )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_role (
                          id,
                          role_name
                      )
                      VALUES (
                          4,
                          'Technical Advisor'
                      )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_role (
                          id,
                          role_name
                      )
                      VALUES (
                          5,
                          'Organization Assistant'
                      )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_role (
                          id,
                          role_name
                      )
                      VALUES (
                          6,
                          'Maintenance Technician'
                      )
ON CONFLICT(id) DO NOTHING;

INSERT INTO user_role (
                          id,
                          role_name
                      )
                      VALUES (
                          7,
                          'Dispatch Supervisor'
                      )
ON CONFLICT(id) DO NOTHING;
-- Table: user_sequence

COMMIT TRANSACTION;
