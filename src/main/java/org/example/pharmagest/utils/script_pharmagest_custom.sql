PGDMP      7                |         
   Pharmagest    16.2    16.2 ^    e           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            f           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            g           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            h           1262    16628 
   Pharmagest    DATABASE        CREATE DATABASE "Pharmagest" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';
    DROP DATABASE "Pharmagest";
                postgres    false            �            1255    17322 ,   get_clients_by_medicament(character varying) 	   PROCEDURE     C  CREATE PROCEDURE public.get_clients_by_medicament(IN p_nom_medicament character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    CREATE TEMPORARY TABLE clients_temp AS
    SELECT c.id_client, c.nom_client, c.prenom_client, c.telephone_client
    FROM client c
    INNER JOIN ventes_payees vp ON c.id_client = vp.id_client
    INNER JOIN vente v ON vp.id_vente = v.id_vente
    INNER JOIN ligne_vente lv ON v.id_vente = lv.id_vente
    INNER JOIN medicament m ON lv.id_medicament = m.id_medicament
    WHERE m.nom_medicament = p_nom_medicament
    GROUP BY c.id_client;
END;
$$;
 X   DROP PROCEDURE public.get_clients_by_medicament(IN p_nom_medicament character varying);
       public          postgres    false            �            1255    16712    update_login_historique()    FUNCTION     �  CREATE FUNCTION public.update_login_historique() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM login_historique WHERE id_utilisateur = NEW.id_utilisateur) THEN
        UPDATE login_historique
        SET date_heure = CURRENT_TIMESTAMP,
            nom_utilisateur = NEW.nom_utilisateur,
            role = NEW.role
        WHERE id_utilisateur = NEW.id_utilisateur;
    ELSE
        INSERT INTO login_historique (id_utilisateur, nom_utilisateur, role, date_heure)
        VALUES (NEW.id_utilisateur, NEW.nom_utilisateur, NEW.role, CURRENT_TIMESTAMP);
    END IF;
    RETURN NULL; -- Changement ici
END;
$$;
 0   DROP FUNCTION public.update_login_historique();
       public          postgres    false            �            1259    16890    approvisionnement    TABLE     �  CREATE TABLE public.approvisionnement (
    id_approvisionnement integer NOT NULL,
    id_medicament integer,
    id_fournisseur integer,
    quantite_commandee integer NOT NULL,
    date_approvisionnement date DEFAULT CURRENT_DATE,
    statut character varying(20) DEFAULT 'en attente'::character varying,
    prix_fournisseur numeric(10,2),
    quantite_recue integer,
    commentaire character varying(255)
);
 %   DROP TABLE public.approvisionnement;
       public         heap    postgres    false            �            1259    16889 *   approvisionnement_id_approvisionnement_seq    SEQUENCE     �   CREATE SEQUENCE public.approvisionnement_id_approvisionnement_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE public.approvisionnement_id_approvisionnement_seq;
       public          postgres    false    232            i           0    0 *   approvisionnement_id_approvisionnement_seq    SEQUENCE OWNED BY     y   ALTER SEQUENCE public.approvisionnement_id_approvisionnement_seq OWNED BY public.approvisionnement.id_approvisionnement;
          public          postgres    false    231            �            1259    16634    client    TABLE     �  CREATE TABLE public.client (
    id_client integer NOT NULL,
    nom_client character varying(50) NOT NULL,
    prenom_client character varying(50) NOT NULL,
    date_naissance_client date,
    adresse_client character varying(200),
    telephone_client character varying(20),
    date_creation date DEFAULT CURRENT_DATE,
    statut character varying(20) DEFAULT 'actif'::character varying
);
    DROP TABLE public.client;
       public         heap    postgres    false            �            1259    16638    client_id_client_seq    SEQUENCE     �   CREATE SEQUENCE public.client_id_client_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.client_id_client_seq;
       public          postgres    false    217            j           0    0    client_id_client_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_id_client_seq OWNED BY public.client.id_client;
          public          postgres    false    218            �            1259    16677    famille    TABLE     �   CREATE TABLE public.famille (
    id_famille integer NOT NULL,
    nom_famille character varying(255) NOT NULL,
    statut character varying(20) DEFAULT 'actif'::character varying,
    date_creation date DEFAULT CURRENT_DATE
);
    DROP TABLE public.famille;
       public         heap    postgres    false            �            1259    16676    famille_id_famille_seq    SEQUENCE     �   CREATE SEQUENCE public.famille_id_famille_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.famille_id_famille_seq;
       public          postgres    false    222            k           0    0    famille_id_famille_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.famille_id_famille_seq OWNED BY public.famille.id_famille;
          public          postgres    false    221            �            1259    16686    forme    TABLE     �   CREATE TABLE public.forme (
    id_forme integer NOT NULL,
    nom_forme character varying(255) NOT NULL,
    statut character varying(20) DEFAULT 'actif'::character varying,
    date_creation date DEFAULT CURRENT_DATE
);
    DROP TABLE public.forme;
       public         heap    postgres    false            �            1259    16685    forme_id_forme_seq    SEQUENCE     �   CREATE SEQUENCE public.forme_id_forme_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.forme_id_forme_seq;
       public          postgres    false    224            l           0    0    forme_id_forme_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.forme_id_forme_seq OWNED BY public.forme.id_forme;
          public          postgres    false    223            �            1259    16852    fournisseur    TABLE     �  CREATE TABLE public.fournisseur (
    id_fournisseur integer NOT NULL,
    nom_fournisseur character varying(255) NOT NULL,
    email_fournisseur character varying(255) NOT NULL,
    tel_fournisseur character varying(20) NOT NULL,
    adresse_fournisseur character varying(255) NOT NULL,
    statut character varying(20) DEFAULT 'actif'::character varying,
    date_creation date DEFAULT CURRENT_DATE
);
    DROP TABLE public.fournisseur;
       public         heap    postgres    false            �            1259    16851    fournisseur_id_fournisseur_seq    SEQUENCE     �   CREATE SEQUENCE public.fournisseur_id_fournisseur_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.fournisseur_id_fournisseur_seq;
       public          postgres    false    228            m           0    0    fournisseur_id_fournisseur_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.fournisseur_id_fournisseur_seq OWNED BY public.fournisseur.id_fournisseur;
          public          postgres    false    227            �            1259    17135    ligne_vente    TABLE     �   CREATE TABLE public.ligne_vente (
    id_ligne_vente integer NOT NULL,
    id_vente integer,
    id_medicament integer,
    quantite integer,
    prix_unitaire numeric(10,2),
    prix_total numeric(10,2)
);
    DROP TABLE public.ligne_vente;
       public         heap    postgres    false            �            1259    17134    ligne_vente_id_ligne_vente_seq    SEQUENCE     �   CREATE SEQUENCE public.ligne_vente_id_ligne_vente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.ligne_vente_id_ligne_vente_seq;
       public          postgres    false    236            n           0    0    ligne_vente_id_ligne_vente_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.ligne_vente_id_ligne_vente_seq OWNED BY public.ligne_vente.id_ligne_vente;
          public          postgres    false    235            �            1259    16695    login_historique    TABLE     �   CREATE TABLE public.login_historique (
    id_login integer NOT NULL,
    id_utilisateur integer,
    nom_utilisateur character varying(255),
    role character varying(50),
    date_heure timestamp without time zone
);
 $   DROP TABLE public.login_historique;
       public         heap    postgres    false            �            1259    16694    login_historique_id_login_seq    SEQUENCE     �   CREATE SEQUENCE public.login_historique_id_login_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.login_historique_id_login_seq;
       public          postgres    false    226            o           0    0    login_historique_id_login_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.login_historique_id_login_seq OWNED BY public.login_historique.id_login;
          public          postgres    false    225            �            1259    16863 
   medicament    TABLE     �  CREATE TABLE public.medicament (
    id_medicament integer NOT NULL,
    nom_medicament character varying(255) NOT NULL,
    description_medicament text,
    id_fournisseur integer,
    id_famille integer,
    id_forme integer,
    statut character varying(20) DEFAULT 'actif'::character varying,
    quantite_medicament integer,
    prix_vente numeric(10,2),
    prix_fournisseur numeric(10,2)
);
    DROP TABLE public.medicament;
       public         heap    postgres    false            �            1259    16862    medicament_id_medicament_seq    SEQUENCE     �   CREATE SEQUENCE public.medicament_id_medicament_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.medicament_id_medicament_seq;
       public          postgres    false    230            p           0    0    medicament_id_medicament_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.medicament_id_medicament_seq OWNED BY public.medicament.id_medicament;
          public          postgres    false    229            �            1259    16645    utilisateurs    TABLE     �   CREATE TABLE public.utilisateurs (
    id_utilisateur integer NOT NULL,
    nom_utilisateur character varying(255) NOT NULL,
    mot_de_passe character varying(255) NOT NULL,
    role character varying(50) NOT NULL,
    date_creation date
);
     DROP TABLE public.utilisateurs;
       public         heap    postgres    false            �            1259    16650    utilisateurs_id_utilisateur_seq    SEQUENCE     �   CREATE SEQUENCE public.utilisateurs_id_utilisateur_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.utilisateurs_id_utilisateur_seq;
       public          postgres    false    219            q           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.utilisateurs_id_utilisateur_seq OWNED BY public.utilisateurs.id_utilisateur;
          public          postgres    false    220            �            1259    17123    vente    TABLE     �   CREATE TABLE public.vente (
    id_vente integer NOT NULL,
    id_client integer,
    type_vente character varying(50),
    montant_total numeric(10,2),
    date_vente date,
    statut character varying(20)
);
    DROP TABLE public.vente;
       public         heap    postgres    false            �            1259    17122    vente_id_vente_seq    SEQUENCE     �   CREATE SEQUENCE public.vente_id_vente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.vente_id_vente_seq;
       public          postgres    false    234            r           0    0    vente_id_vente_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.vente_id_vente_seq OWNED BY public.vente.id_vente;
          public          postgres    false    233            �            1259    17316    ventes_payees    TABLE       CREATE TABLE public.ventes_payees (
    id_vente_payee integer NOT NULL,
    id_vente integer NOT NULL,
    id_client integer,
    type_vente character varying(50) NOT NULL,
    montant_total numeric(10,2) NOT NULL,
    date_vente date NOT NULL,
    date_paiement date NOT NULL
);
 !   DROP TABLE public.ventes_payees;
       public         heap    postgres    false            �            1259    17315     ventes_payees_id_vente_payee_seq    SEQUENCE     �   CREATE SEQUENCE public.ventes_payees_id_vente_payee_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.ventes_payees_id_vente_payee_seq;
       public          postgres    false    238            s           0    0     ventes_payees_id_vente_payee_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.ventes_payees_id_vente_payee_seq OWNED BY public.ventes_payees.id_vente_payee;
          public          postgres    false    237            �           2604    16893 &   approvisionnement id_approvisionnement    DEFAULT     �   ALTER TABLE ONLY public.approvisionnement ALTER COLUMN id_approvisionnement SET DEFAULT nextval('public.approvisionnement_id_approvisionnement_seq'::regclass);
 U   ALTER TABLE public.approvisionnement ALTER COLUMN id_approvisionnement DROP DEFAULT;
       public          postgres    false    231    232    232            �           2604    16652    client id_client    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN id_client SET DEFAULT nextval('public.client_id_client_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN id_client DROP DEFAULT;
       public          postgres    false    218    217            �           2604    16680    famille id_famille    DEFAULT     x   ALTER TABLE ONLY public.famille ALTER COLUMN id_famille SET DEFAULT nextval('public.famille_id_famille_seq'::regclass);
 A   ALTER TABLE public.famille ALTER COLUMN id_famille DROP DEFAULT;
       public          postgres    false    221    222    222            �           2604    16689    forme id_forme    DEFAULT     p   ALTER TABLE ONLY public.forme ALTER COLUMN id_forme SET DEFAULT nextval('public.forme_id_forme_seq'::regclass);
 =   ALTER TABLE public.forme ALTER COLUMN id_forme DROP DEFAULT;
       public          postgres    false    223    224    224            �           2604    16855    fournisseur id_fournisseur    DEFAULT     �   ALTER TABLE ONLY public.fournisseur ALTER COLUMN id_fournisseur SET DEFAULT nextval('public.fournisseur_id_fournisseur_seq'::regclass);
 I   ALTER TABLE public.fournisseur ALTER COLUMN id_fournisseur DROP DEFAULT;
       public          postgres    false    227    228    228            �           2604    17138    ligne_vente id_ligne_vente    DEFAULT     �   ALTER TABLE ONLY public.ligne_vente ALTER COLUMN id_ligne_vente SET DEFAULT nextval('public.ligne_vente_id_ligne_vente_seq'::regclass);
 I   ALTER TABLE public.ligne_vente ALTER COLUMN id_ligne_vente DROP DEFAULT;
       public          postgres    false    236    235    236            �           2604    16698    login_historique id_login    DEFAULT     �   ALTER TABLE ONLY public.login_historique ALTER COLUMN id_login SET DEFAULT nextval('public.login_historique_id_login_seq'::regclass);
 H   ALTER TABLE public.login_historique ALTER COLUMN id_login DROP DEFAULT;
       public          postgres    false    225    226    226            �           2604    16866    medicament id_medicament    DEFAULT     �   ALTER TABLE ONLY public.medicament ALTER COLUMN id_medicament SET DEFAULT nextval('public.medicament_id_medicament_seq'::regclass);
 G   ALTER TABLE public.medicament ALTER COLUMN id_medicament DROP DEFAULT;
       public          postgres    false    230    229    230            �           2604    16654    utilisateurs id_utilisateur    DEFAULT     �   ALTER TABLE ONLY public.utilisateurs ALTER COLUMN id_utilisateur SET DEFAULT nextval('public.utilisateurs_id_utilisateur_seq'::regclass);
 J   ALTER TABLE public.utilisateurs ALTER COLUMN id_utilisateur DROP DEFAULT;
       public          postgres    false    220    219            �           2604    17126    vente id_vente    DEFAULT     p   ALTER TABLE ONLY public.vente ALTER COLUMN id_vente SET DEFAULT nextval('public.vente_id_vente_seq'::regclass);
 =   ALTER TABLE public.vente ALTER COLUMN id_vente DROP DEFAULT;
       public          postgres    false    233    234    234            �           2604    17319    ventes_payees id_vente_payee    DEFAULT     �   ALTER TABLE ONLY public.ventes_payees ALTER COLUMN id_vente_payee SET DEFAULT nextval('public.ventes_payees_id_vente_payee_seq'::regclass);
 K   ALTER TABLE public.ventes_payees ALTER COLUMN id_vente_payee DROP DEFAULT;
       public          postgres    false    237    238    238            \          0    16890    approvisionnement 
   TABLE DATA           �   COPY public.approvisionnement (id_approvisionnement, id_medicament, id_fournisseur, quantite_commandee, date_approvisionnement, statut, prix_fournisseur, quantite_recue, commentaire) FROM stdin;
    public          postgres    false    232   �}       M          0    16634    client 
   TABLE DATA           �   COPY public.client (id_client, nom_client, prenom_client, date_naissance_client, adresse_client, telephone_client, date_creation, statut) FROM stdin;
    public          postgres    false    217   m~       R          0    16677    famille 
   TABLE DATA           Q   COPY public.famille (id_famille, nom_famille, statut, date_creation) FROM stdin;
    public          postgres    false    222   �       T          0    16686    forme 
   TABLE DATA           K   COPY public.forme (id_forme, nom_forme, statut, date_creation) FROM stdin;
    public          postgres    false    224   ��       X          0    16852    fournisseur 
   TABLE DATA           �   COPY public.fournisseur (id_fournisseur, nom_fournisseur, email_fournisseur, tel_fournisseur, adresse_fournisseur, statut, date_creation) FROM stdin;
    public          postgres    false    228   R�       `          0    17135    ligne_vente 
   TABLE DATA           s   COPY public.ligne_vente (id_ligne_vente, id_vente, id_medicament, quantite, prix_unitaire, prix_total) FROM stdin;
    public          postgres    false    236   :�       V          0    16695    login_historique 
   TABLE DATA           g   COPY public.login_historique (id_login, id_utilisateur, nom_utilisateur, role, date_heure) FROM stdin;
    public          postgres    false    226   p�       Z          0    16863 
   medicament 
   TABLE DATA           �   COPY public.medicament (id_medicament, nom_medicament, description_medicament, id_fournisseur, id_famille, id_forme, statut, quantite_medicament, prix_vente, prix_fournisseur) FROM stdin;
    public          postgres    false    230   ��       O          0    16645    utilisateurs 
   TABLE DATA           j   COPY public.utilisateurs (id_utilisateur, nom_utilisateur, mot_de_passe, role, date_creation) FROM stdin;
    public          postgres    false    219   ��       ^          0    17123    vente 
   TABLE DATA           c   COPY public.vente (id_vente, id_client, type_vente, montant_total, date_vente, statut) FROM stdin;
    public          postgres    false    234   އ       b          0    17316    ventes_payees 
   TABLE DATA           �   COPY public.ventes_payees (id_vente_payee, id_vente, id_client, type_vente, montant_total, date_vente, date_paiement) FROM stdin;
    public          postgres    false    238          t           0    0 *   approvisionnement_id_approvisionnement_seq    SEQUENCE SET     Y   SELECT pg_catalog.setval('public.approvisionnement_id_approvisionnement_seq', 21, true);
          public          postgres    false    231            u           0    0    client_id_client_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.client_id_client_seq', 16, true);
          public          postgres    false    218            v           0    0    famille_id_famille_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.famille_id_famille_seq', 27, true);
          public          postgres    false    221            w           0    0    forme_id_forme_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.forme_id_forme_seq', 12, true);
          public          postgres    false    223            x           0    0    fournisseur_id_fournisseur_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.fournisseur_id_fournisseur_seq', 10, true);
          public          postgres    false    227            y           0    0    ligne_vente_id_ligne_vente_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.ligne_vente_id_ligne_vente_seq', 41, true);
          public          postgres    false    235            z           0    0    login_historique_id_login_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.login_historique_id_login_seq', 355, true);
          public          postgres    false    225            {           0    0    medicament_id_medicament_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.medicament_id_medicament_seq', 11, true);
          public          postgres    false    229            |           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.utilisateurs_id_utilisateur_seq', 45, true);
          public          postgres    false    220            }           0    0    vente_id_vente_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.vente_id_vente_seq', 22, true);
          public          postgres    false    233            ~           0    0     ventes_payees_id_vente_payee_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.ventes_payees_id_vente_payee_seq', 19, true);
          public          postgres    false    237            �           2606    16897 (   approvisionnement approvisionnement_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.approvisionnement
    ADD CONSTRAINT approvisionnement_pkey PRIMARY KEY (id_approvisionnement);
 R   ALTER TABLE ONLY public.approvisionnement DROP CONSTRAINT approvisionnement_pkey;
       public            postgres    false    232            �           2606    16658    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id_client);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    217            �           2606    16684    famille famille_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.famille
    ADD CONSTRAINT famille_pkey PRIMARY KEY (id_famille);
 >   ALTER TABLE ONLY public.famille DROP CONSTRAINT famille_pkey;
       public            postgres    false    222            �           2606    16693    forme forme_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.forme
    ADD CONSTRAINT forme_pkey PRIMARY KEY (id_forme);
 :   ALTER TABLE ONLY public.forme DROP CONSTRAINT forme_pkey;
       public            postgres    false    224            �           2606    16861    fournisseur fournisseur_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.fournisseur
    ADD CONSTRAINT fournisseur_pkey PRIMARY KEY (id_fournisseur);
 F   ALTER TABLE ONLY public.fournisseur DROP CONSTRAINT fournisseur_pkey;
       public            postgres    false    228            �           2606    17140    ligne_vente ligne_vente_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.ligne_vente
    ADD CONSTRAINT ligne_vente_pkey PRIMARY KEY (id_ligne_vente);
 F   ALTER TABLE ONLY public.ligne_vente DROP CONSTRAINT ligne_vente_pkey;
       public            postgres    false    236            �           2606    16720 4   login_historique login_historique_id_utilisateur_key 
   CONSTRAINT     y   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_id_utilisateur_key UNIQUE (id_utilisateur);
 ^   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_id_utilisateur_key;
       public            postgres    false    226            �           2606    16700 &   login_historique login_historique_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_pkey PRIMARY KEY (id_login);
 P   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_pkey;
       public            postgres    false    226            �           2606    16871    medicament medicament_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_pkey PRIMARY KEY (id_medicament);
 D   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_pkey;
       public            postgres    false    230            �           2606    16662    utilisateurs utilisateurs_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id_utilisateur);
 H   ALTER TABLE ONLY public.utilisateurs DROP CONSTRAINT utilisateurs_pkey;
       public            postgres    false    219            �           2606    17128    vente vente_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.vente
    ADD CONSTRAINT vente_pkey PRIMARY KEY (id_vente);
 :   ALTER TABLE ONLY public.vente DROP CONSTRAINT vente_pkey;
       public            postgres    false    234            �           2606    17321     ventes_payees ventes_payees_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.ventes_payees
    ADD CONSTRAINT ventes_payees_pkey PRIMARY KEY (id_vente_payee);
 J   ALTER TABLE ONLY public.ventes_payees DROP CONSTRAINT ventes_payees_pkey;
       public            postgres    false    238            �           2620    16714 $   utilisateurs update_login_historique    TRIGGER     �   CREATE TRIGGER update_login_historique AFTER INSERT OR UPDATE ON public.utilisateurs FOR EACH ROW EXECUTE FUNCTION public.update_login_historique();
 =   DROP TRIGGER update_login_historique ON public.utilisateurs;
       public          postgres    false    239    219            �           2606    16903 7   approvisionnement approvisionnement_id_fournisseur_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.approvisionnement
    ADD CONSTRAINT approvisionnement_id_fournisseur_fkey FOREIGN KEY (id_fournisseur) REFERENCES public.fournisseur(id_fournisseur);
 a   ALTER TABLE ONLY public.approvisionnement DROP CONSTRAINT approvisionnement_id_fournisseur_fkey;
       public          postgres    false    232    228    4777            �           2606    16898 6   approvisionnement approvisionnement_id_medicament_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.approvisionnement
    ADD CONSTRAINT approvisionnement_id_medicament_fkey FOREIGN KEY (id_medicament) REFERENCES public.medicament(id_medicament);
 `   ALTER TABLE ONLY public.approvisionnement DROP CONSTRAINT approvisionnement_id_medicament_fkey;
       public          postgres    false    4779    230    232            �           2606    17146 *   ligne_vente ligne_vente_id_medicament_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ligne_vente
    ADD CONSTRAINT ligne_vente_id_medicament_fkey FOREIGN KEY (id_medicament) REFERENCES public.medicament(id_medicament);
 T   ALTER TABLE ONLY public.ligne_vente DROP CONSTRAINT ligne_vente_id_medicament_fkey;
       public          postgres    false    230    4779    236            �           2606    17141 %   ligne_vente ligne_vente_id_vente_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ligne_vente
    ADD CONSTRAINT ligne_vente_id_vente_fkey FOREIGN KEY (id_vente) REFERENCES public.vente(id_vente);
 O   ALTER TABLE ONLY public.ligne_vente DROP CONSTRAINT ligne_vente_id_vente_fkey;
       public          postgres    false    234    236    4783            �           2606    16701 5   login_historique login_historique_id_utilisateur_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_id_utilisateur_fkey FOREIGN KEY (id_utilisateur) REFERENCES public.utilisateurs(id_utilisateur);
 _   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_id_utilisateur_fkey;
       public          postgres    false    4767    219    226            �           2606    16877 %   medicament medicament_id_famille_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_famille_fkey FOREIGN KEY (id_famille) REFERENCES public.famille(id_famille);
 O   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_famille_fkey;
       public          postgres    false    230    222    4769            �           2606    16882 #   medicament medicament_id_forme_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_forme_fkey FOREIGN KEY (id_forme) REFERENCES public.forme(id_forme);
 M   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_forme_fkey;
       public          postgres    false    4771    224    230            �           2606    16872 )   medicament medicament_id_fournisseur_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_fournisseur_fkey FOREIGN KEY (id_fournisseur) REFERENCES public.fournisseur(id_fournisseur);
 S   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_fournisseur_fkey;
       public          postgres    false    4777    228    230            �           2606    17129    vente vente_id_client_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.vente
    ADD CONSTRAINT vente_id_client_fkey FOREIGN KEY (id_client) REFERENCES public.client(id_client);
 D   ALTER TABLE ONLY public.vente DROP CONSTRAINT vente_id_client_fkey;
       public          postgres    false    217    234    4765            \   �   x�eα
�0����],�&IGW�.����3� ��l� ����,��&ݞ��p��犎"�W\�!�!��(�	��������#�Ilf8���~���ƥ*M���R�>���K�r*Qi���q�L�Ɛ��QJ���7      M   ~  x�}�Mn�0�׏S� u�_�,QTUEB��ƀ)�R9	*G�Q8��T/�{�{�	��v�]�F;����p�P�Bk�ڠJ���_wq[��9.T	�0�ɩ�j�f�"-h��i���+	&��@zo\O���V�j|����h��T�X
��-��l�r,�n��1m�Ix
ۢg�.}>�B[���|8/��[�|ޅn���5!t�Ba�0��PdVf�ú�1�}�tp��͵��B�!Y���`�o]GV;Ɯ -�-���W�w��!N�\o|Xx���5�1%&
&��zJ�O,{)���M����?6�+h��(0%�����su�s�KD�ie�e$_�|�N	�e��E<OF�Ɂ�sӛ6�<�^��J���������Ѥ      R   �  x�}�Kn�0���S�*,��d�A�E#Nv�L%Z@�C����s�b9	��h����σ�p۶Vjn�&X:�Y�u�������>4���f2��@��ٕ�ۚ�D�Ϗ��1��-v��$s��Е� )e6d}�)n��T�GW���+�cK�OeL�+?�+����[]緯ổ������r)�X�� �߻0��?�C�V,
x8�q(��U������6`[�S��t^kxr�۟�̳
zJg����7��Sh�м;�-����U��ˆP�9�+x��R�}ρsi��y�rtI7�s6&��v�U�ೊ�v�ȓ�3�j=��� �6���M/M�`c`��35�R-���5C�`ϱl4�#w��j�&r���Bn���Q�@fo_ӦF�I���-�w�PS      T   �   x�m�M
�0����������Lch�̐����{a�Ջ��|��o�j�-��⛞f7z�?ޯ'��t��?e`�؁�\� P�&�T��p�������x�����T�	�h#aj=���G*�-�Ăh�%�q�@����D(�ܶJ�/19S;      X   �  x�m�͎�0F��S� 3#���i�.&J���q�3��eC4y��9�b�؈I!;|�w?(��F�k�R��<vp���P���y�RJe��Q�A&���Nv��pq��c����F������{��	ݤ�:׉�{'Z�섆�m��qQ�P���e(�ɳZy�	�J�pIG�V8/U��5��ZT���{�#H���(�2��(��"�MF/��Nu�3`R2b~�7��ߠd���{�brѨ	�9;BS�q��uv�.�I��#g�?fp�k�H�3���3Sr4͋r�t��^9c���H�!t/�������g��@P��̞:k�I[p{)�$񪴼���r�%����C�Ұ�g�)��5�����jyVܾ��e�l��:q�!��-+���w)��0��a��>�x��_�ȏ^��I�o-�L���u��O����A 8;m/�Ѿ�`y�U©������?�      `   &  x�m�K�1D��0�����1��NleA�
An&�IB\�V�E@&颚T�0 �G�A0��=��P:5O�+���lޱ?kO���h���o(,o:2����bb�B��,�-���A5����F�.��w���䃕�6b��1���Ye$��m�'�>�!���1���ACpC�f����)�߻O����+��N/i:7
��,9��.���2�ө��fǶE�c����Ra��~�Ӈ�a��% �2]��������9��'o�U�?�.�pB9m��UJ�B��      V   |   x�e�1�0 ��~he;�C����`A�D�o�`�r�%aHж9�l��~���@6�_�T���1S)*� �x�S�}��S����JrP�6����o����oS7G�{�����>*ec�ۈ���)n      Z   }  x����J�0ǯO������.'
�
�`Wބ���IMS���]�}1�N:��HI�@�����"p88�|* J!�e�TY�2\����M$���Pk��@��T�uDHH���%�>f�E��U��YW�����I��`��cJ�߅�y�19l:,��g��P��R�M�#� �s���W� m��*�\6�֐oз�<���"��X̀x�cD��f��`���ƀ��G�Pӭ�`8h����HRZ3*#�O�s)<I��c))�2\�m9-�{�'���Y1��2���F+[J�bf�b��z�4�z�U� W?g�5�� ��iEv��4�R����kt��9i���lH�Ө]f�W�`����-γ2�s>	i��*�9/!�'q��      O   E   x�3�L�KɄe�y)��E�1~\F�9���"91��83,nș��Ɖ)��y !S(����qqq �	�      ^   �   x�u�A�@�u9��t:��^@Mܲ!�ML<���b����N^���I���q8���4��0�O�3	K*YK����g,"	]����Eh�`fG4Z�;��{��0u�0;{I��Rc�j�k�e�5�����m� }$�<����ك&ĳ�����x/��cy��ļ�i�ٚ	�֋"����7ZW=�4�1��}ǝ΋(~��      b   q   x�}�;�0��z|
. [I%'���&bi��"q~R���~�<��.����rNyY�;Fs-}KW�h� 攷���������*�l3E�wݧ|e���B���D� &i<�     