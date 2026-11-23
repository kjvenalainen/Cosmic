-- Rebalance regular throwing-star purchase prices, keeping the existing tier curve.
-- Steely Throwing-Knives (+25 attack) are the 500,000-meso anchor.
UPDATE shopitems
SET price = CASE itemid
    WHEN 2070000 THEN 6000       -- Subi (+15)
    WHEN 2070001 THEN 15000      -- Wolbi (+17)
    WHEN 2070002 THEN 35000      -- Mokbi (+19)
    WHEN 2070003 THEN 85000      -- Kumbi (+21)
    WHEN 2070004 THEN 205000     -- Tobi (+23)
    WHEN 2070005 THEN 500000     -- Steely (+25)
    WHEN 2070006 THEN 1200000    -- Ilbi (+27)
    WHEN 2070007 THEN 1200000    -- Hwabi (+27)
END
WHERE itemid IN (2070000, 2070001, 2070002, 2070003, 2070004, 2070005, 2070006, 2070007);
