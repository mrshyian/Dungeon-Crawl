DROP TABLE IF EXISTS public.game_state CASCADE;
CREATE TABLE public.game_state
(
    id          serial                                                NOT NULL PRIMARY KEY,
    current_map text                                                  NOT NULL,
    saved_at    timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_name text                                                  NOT NULL UNIQUE
);

DROP TABLE IF EXISTS public.opponent CASCADE;
CREATE TABLE public.opponent
(
    id     serial  NOT NULL PRIMARY KEY,
    x      integer NOT NULL,
    y      integer NOT NULL,
    name   text    NOT NULL,
    health integer NOT NULL,
    power  integer NOT NULL,
    shield integer NOT NULL
);

DROP TABLE IF EXISTS public.item CASCADE;
CREATE TABLE public.item
(
    id     serial  NOT NULL PRIMARY KEY,
    x      integer NOT NULL,
    y      integer NOT NULL,
    name   text    NOT NULL,
    health integer NOT NULL,
    power  integer NOT NULL,
    shield integer NOT NULL
);

DROP TABLE IF EXISTS public.player CASCADE;
CREATE TABLE public.player
(
    id         serial  NOT NULL,
    name       text    NOT NULL PRIMARY KEY UNIQUE,
    playerView text    NOT NULL,
    x          integer NOT NULL,
    y          integer NOT NULL,
    health     integer NOT NULL,
    power      integer NOT NULL,
    shield     integer NOT NULL
);

DROP TABLE IF EXISTS public.door CASCADE;
CREATE TABLE public.door
(
    id      serial  NOT NULL PRIMARY KEY,
    x       integer NOT NULL,
    y       integer NOT NULL,
    is_open boolean NOT NULL
);


DROP TABLE IF EXISTS public.game_opponents CASCADE;
CREATE TABLE public.game_opponents
(
    id          serial  NOT NULL PRIMARY KEY,
    game_id     integer NOT NULL,
    opponent_id integer NOT NULL
);

DROP TABLE IF EXISTS public.game_items CASCADE;
CREATE TABLE public.game_items
(
    id      serial  NOT NULL PRIMARY KEY,
    game_id integer NOT NULL,
    item_id integer NOT NULL
);

DROP TABLE IF EXISTS public.player_backpack CASCADE;
CREATE TABLE public.player_backpack
(
    id          serial  NOT NULL PRIMARY KEY,
    player_name text    NOT NULL,
    item_id     integer NOT NULL
);

DROP TABLE IF EXISTS public.game_doors CASCADE;
CREATE TABLE public.game_doors
(
    id      serial  NOT NULL PRIMARY KEY,
    game_id int     NOT NULL,
    door_id integer NOT NULL
);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_name) REFERENCES public.player (name) ON DELETE CASCADE;

ALTER TABLE ONLY public.game_opponents
    ADD CONSTRAINT fk_opponent_id FOREIGN KEY (opponent_id) REFERENCES public.opponent (id) ON DELETE CASCADE;

ALTER TABLE ONLY public.game_doors
    ADD CONSTRAINT fk_door_id FOREIGN KEY (door_id) REFERENCES public.door (id) ON DELETE CASCADE;

ALTER TABLE ONLY public.game_items
    ADD CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES public.item (id) ON DELETE CASCADE;

ALTER TABLE ONLY public.player_backpack
    ADD CONSTRAINT fk_player_name FOREIGN KEY (player_name) REFERENCES public.player (name) ON DELETE CASCADE;

ALTER TABLE ONLY public.player_backpack
    ADD CONSTRAINT fk_player_item_id FOREIGN KEY (item_id) REFERENCES public.item (id) ON DELETE CASCADE;

ALTER TABLE ONLY public.game_items
    ADD CONSTRAINT fk_game_items_id FOREIGN KEY (game_id) REFERENCES public.game_state (id) ON DELETE CASCADE;

ALTER TABLE ONLY public.game_opponents
    ADD CONSTRAINT fk_game_opponents_id FOREIGN KEY (game_id) REFERENCES public.game_state (id) ON DELETE CASCADE;

ALTER TABLE ONLY public.game_doors
    ADD CONSTRAINT fk_game_doors_id FOREIGN KEY (game_id) REFERENCES public.game_state (id) ON DELETE CASCADE;

CREATE OR REPLACE FUNCTION remove_game_item() RETURNS trigger AS
$$BEGIN
    DELETE FROM item where id = OLD.item_id;
    RETURN OLD;
END$$ language plpgsql;

CREATE TRIGGER remove_game_items AFTER DELETE ON game_items FOR EACH ROW EXECUTE FUNCTION remove_game_item();

CREATE OR REPLACE FUNCTION remove_game_opponent() RETURNS trigger AS
$$BEGIN
    DELETE FROM opponent where id = OLD.opponent_id;
    RETURN OLD;
END$$ language plpgsql;

CREATE TRIGGER remove_game_opponents AFTER DELETE ON game_opponents FOR EACH ROW EXECUTE FUNCTION remove_game_opponent();

CREATE OR REPLACE FUNCTION remove_game_door() RETURNS trigger AS
$$BEGIN
    DELETE FROM door where id = OLD.door_id;
    RETURN OLD;
END$$ language plpgsql;

CREATE TRIGGER remove_game_doors AFTER DELETE ON game_doors FOR EACH ROW EXECUTE FUNCTION remove_game_door();

CREATE OR REPLACE FUNCTION remove_player_backpack_item() RETURNS trigger AS
$$BEGIN
    DELETE FROM item where id = OLD.item_id;
    RETURN OLD;
END$$ language plpgsql;

CREATE TRIGGER remove_player_backpack_items AFTER DELETE ON player_backpack FOR EACH ROW EXECUTE FUNCTION remove_player_backpack_item();

