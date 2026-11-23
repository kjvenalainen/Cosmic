-- Kerning City: keep the base claw in each family and leave variants to crafting and drops.
DELETE FROM shopitems
WHERE shopid = 1051000 AND itemid IN (
    1472002, 1472003,                   -- Steel Titans variants
    1472005, 1472006,                   -- Bronze Igor variants
    1472009, 1472010,                   -- Steel Guards variants
    1472012, 1472013,                   -- Bronze Guardian variants
    1472015, 1472016, 1472017,          -- Steel Avarice variants
    1472019, 1472020, 1472021,          -- Steel Slain variants
    1472023, 1472024, 1472025,          -- Bronze Gigantic variants
    1472027, 1472028, 1472029           -- Brown Scarab variants
);

-- Replace reward variants with regular weapons in the shop's existing level range.
-- Keep the town shop markup of twice each replacement weapon's WZ price.

-- Henesys: Asianic Bow, Golden Crow, Golden Hinkel. The regular Gross Jaeger is already stocked.
UPDATE shopitems SET itemid = 1452004, price = 500000 WHERE shopid = 1011000 AND itemid = 1452030;
UPDATE shopitems SET itemid = 1462008, price = 480000 WHERE shopid = 1011000 AND itemid = 1462031;
UPDATE shopitems SET itemid = 1452011, price = 600000 WHERE shopid = 1011000 AND itemid = 1452031;

-- Perion: swords, axes, blunt weapons, spears, and polearms.
UPDATE shopitems SET itemid = 1302010, price = 240000 WHERE shopid = 1021000 AND itemid = 1302045;
UPDATE shopitems SET itemid = 1302011, price = 300000 WHERE shopid = 1021000 AND itemid = 1302053;
UPDATE shopitems SET itemid = 1302012, price = 500000 WHERE shopid = 1021000 AND itemid = 1302042;
UPDATE shopitems SET itemid = 1312008, price = 280000 WHERE shopid = 1021000 AND itemid = 1312027;
UPDATE shopitems SET itemid = 1312009, price = 380000 WHERE shopid = 1021000 AND itemid = 1312028;
UPDATE shopitems SET itemid = 1312010, price = 600000 WHERE shopid = 1021000 AND itemid = 1312029;
UPDATE shopitems SET itemid = 1322017, price = 280000 WHERE shopid = 1021000 AND itemid = 1322037;
UPDATE shopitems SET itemid = 1322018, price = 380000 WHERE shopid = 1021000 AND itemid = 1322043;
UPDATE shopitems SET itemid = 1322019, price = 500000 WHERE shopid = 1021000 AND itemid = 1322039;
UPDATE shopitems SET itemid = 1402003, price = 400000 WHERE shopid = 1021000 AND itemid = 1402026;
UPDATE shopitems SET itemid = 1402011, price = 460000 WHERE shopid = 1021000 AND itemid = 1402027;
UPDATE shopitems SET itemid = 1402012, price = 520000 WHERE shopid = 1021000 AND itemid = 1402028;
UPDATE shopitems SET itemid = 1412004, price = 160000 WHERE shopid = 1021000 AND itemid = 1412013;
UPDATE shopitems SET itemid = 1412003, price = 280000 WHERE shopid = 1021000 AND itemid = 1412023;
UPDATE shopitems SET itemid = 1412007, price = 380000 WHERE shopid = 1021000 AND itemid = 1412015;
UPDATE shopitems SET itemid = 1412008, price = 500000 WHERE shopid = 1021000 AND itemid = 1412020;
UPDATE shopitems SET itemid = 1422008, price = 160000 WHERE shopid = 1021000 AND itemid = 1422023;
UPDATE shopitems SET itemid = 1422005, price = 300000 WHERE shopid = 1021000 AND itemid = 1422016;
UPDATE shopitems SET itemid = 1422009, price = 380000 WHERE shopid = 1021000 AND itemid = 1422021;
UPDATE shopitems SET itemid = 1422010, price = 500000 WHERE shopid = 1021000 AND itemid = 1422022;
UPDATE shopitems SET itemid = 1432003, price = 140000 WHERE shopid = 1021000 AND itemid = 1432025;
UPDATE shopitems SET itemid = 1432004, price = 300000 WHERE shopid = 1021000 AND itemid = 1432026;
UPDATE shopitems SET itemid = 1432006, price = 380000 WHERE shopid = 1021000 AND itemid = 1432022;
UPDATE shopitems SET itemid = 1432007, price = 500000 WHERE shopid = 1021000 AND itemid = 1432023;
UPDATE shopitems SET itemid = 1442003, price = 140000 WHERE shopid = 1021000 AND itemid = 1442031;
UPDATE shopitems SET itemid = 1442005, price = 300000 WHERE shopid = 1021000 AND itemid = 1442032;
UPDATE shopitems SET itemid = 1442010, price = 500000 WHERE shopid = 1021000 AND itemid = 1442033;
UPDATE shopitems SET itemid = 1442008, price = 600000 WHERE shopid = 1021000 AND itemid = 1442034;

-- Ellinia: restore regular wand and staff progression through Angel Wings and Evil Wings.
UPDATE shopitems SET itemid = 1372000, price = 320000 WHERE shopid = 1031000 AND itemid = 1372028;
UPDATE shopitems SET itemid = 1372007, price = 400000 WHERE shopid = 1031000 AND itemid = 1372024;
UPDATE shopitems SET itemid = 1382018, price = 70000 WHERE shopid = 1031000 AND itemid = 1382020;
UPDATE shopitems SET itemid = 1382001, price = 200000 WHERE shopid = 1031000 AND itemid = 1382021;
UPDATE shopitems SET itemid = 1382006, price = 300000 WHERE shopid = 1031000 AND itemid = 1382022;
