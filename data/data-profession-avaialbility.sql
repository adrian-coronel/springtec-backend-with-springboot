use db_springtec;

-- ANTES DE EJECUTAR ESTE SCRIPT DEBES DE CREAR 10 USUARIOS TECNICOS USANDO LA API REST DE SPRINGBOOT
-- YA QUE ESTA GUARDARÁ A LOS USUARIOS CON LA CONTRASEÑA ENCRIPTADA, LO QUE REPERCUTE EN LA VALIDACIÓN DEL TOKEN.


-- CREAR PROFESSION AVAILABILITY
	-- Inserciones para Technical 1
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (1, 3, 1, 3);
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (1, 1, 2, 1);
		-- SU PROFESSION LOCAL
		INSERT INTO profession_local(profession_availability_id,lat,lng,state) VALUES(2,-12.0442274,-76.9504821,'1');
        
	-- Inserciones para Technical 2
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (2, 4, 2, 4);

	-- Inserciones para Technical 3
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (3, 5, 1, 1);
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (3, 2, 2, 2);
		-- SU PROFESSION LOCAL
        INSERT INTO profession_local(profession_availability_id, lat, lng, state) VALUES (5, -12.050123, -76.942345, '1');
        
	-- Inserciones para Technical 4
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (4, 1, 1, 4);

	-- Inserciones para Technical 5
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (5, 3, 1, 2);
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (5, 4, 2, 1);
		-- SU PROFESSION LOCAL
        INSERT INTO profession_local(profession_availability_id, lat, lng, state) VALUES (8, -12.040890, -76.965432, '1');

        
	-- Inserciones para Technical 6
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (6, 3, 2, 3);

	-- Inserciones para Technical 7
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (7, 4, 1, 2);
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (7, 1, 2, 1);
		-- SU PROFESSION LOCAL
        INSERT INTO profession_local(profession_availability_id, lat, lng, state) VALUES (11, -12.048765, -76.960123, '1');
        
	-- Inserciones para Technical 8
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (8, 5, 1, 3);
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (8, 2, 2, 4);
		-- SU PROFESSION LOCAL
        INSERT INTO profession_local(profession_availability_id, lat, lng, state) VALUES (13, -12.038765, -76.948765, '1');
        
	-- Inserciones para Technical 9
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (9, 4, 2, 2);

	-- Inserciones para Technical 10
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (10, 1, 1, 3);
	INSERT INTO profession_availability(technical_id, profession_id, availability_id, experience_id) VALUES (10, 5, 2, 1);
		-- SU PROFESSION LOCAL
		INSERT INTO profession_local(profession_availability_id, lat, lng, state) VALUES (16, -12.042345, -76.970123, '1');
