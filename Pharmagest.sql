PGDMP  $                    |            pharmagest4    16.2    16.2 C    @           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            A           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            B           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            C           1262    32782    pharmagest4    DATABASE     ~   CREATE DATABASE pharmagest4 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';
    DROP DATABASE pharmagest4;
                postgres    false            �            1255    32783    update_login_historique()    FUNCTION     �  CREATE FUNCTION public.update_login_historique() RETURNS trigger
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
       public          postgres    false            �            1259    32784    approvisionnement    TABLE     �  CREATE TABLE public.approvisionnement (
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
       public         heap    postgres    false            �            1259    32789 *   approvisionnement_id_approvisionnement_seq    SEQUENCE     �   CREATE SEQUENCE public.approvisionnement_id_approvisionnement_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE public.approvisionnement_id_approvisionnement_seq;
       public          postgres    false    215            D           0    0 *   approvisionnement_id_approvisionnement_seq    SEQUENCE OWNED BY     y   ALTER SEQUENCE public.approvisionnement_id_approvisionnement_seq OWNED BY public.approvisionnement.id_approvisionnement;
          public          postgres    false    216            �            1259    32790    client    TABLE     �  CREATE TABLE public.client (
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
       public         heap    postgres    false            �            1259    32795    client_id_client_seq    SEQUENCE     �   CREATE SEQUENCE public.client_id_client_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.client_id_client_seq;
       public          postgres    false    217            E           0    0    client_id_client_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_id_client_seq OWNED BY public.client.id_client;
          public          postgres    false    218            �            1259    32796    famille    TABLE     �   CREATE TABLE public.famille (
    id_famille integer NOT NULL,
    nom_famille character varying(255) NOT NULL,
    statut character varying(20) DEFAULT 'actif'::character varying,
    date_creation date DEFAULT CURRENT_DATE
);
    DROP TABLE public.famille;
       public         heap    postgres    false            �            1259    32801    famille_id_famille_seq    SEQUENCE     �   CREATE SEQUENCE public.famille_id_famille_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.famille_id_famille_seq;
       public          postgres    false    219            F           0    0    famille_id_famille_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.famille_id_famille_seq OWNED BY public.famille.id_famille;
          public          postgres    false    220            �            1259    32802    forme    TABLE     �   CREATE TABLE public.forme (
    id_forme integer NOT NULL,
    nom_forme character varying(255) NOT NULL,
    statut character varying(20) DEFAULT 'actif'::character varying,
    date_creation date DEFAULT CURRENT_DATE
);
    DROP TABLE public.forme;
       public         heap    postgres    false            �            1259    32807    forme_id_forme_seq    SEQUENCE     �   CREATE SEQUENCE public.forme_id_forme_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.forme_id_forme_seq;
       public          postgres    false    221            G           0    0    forme_id_forme_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.forme_id_forme_seq OWNED BY public.forme.id_forme;
          public          postgres    false    222            �            1259    32808    fournisseur    TABLE     �  CREATE TABLE public.fournisseur (
    id_fournisseur integer NOT NULL,
    nom_fournisseur character varying(255) NOT NULL,
    email_fournisseur character varying(255) NOT NULL,
    tel_fournisseur character varying(20) NOT NULL,
    adresse_fournisseur character varying(255) NOT NULL,
    statut character varying(20) DEFAULT 'actif'::character varying,
    date_creation date DEFAULT CURRENT_DATE
);
    DROP TABLE public.fournisseur;
       public         heap    postgres    false            �            1259    32815    fournisseur_id_fournisseur_seq    SEQUENCE     �   CREATE SEQUENCE public.fournisseur_id_fournisseur_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.fournisseur_id_fournisseur_seq;
       public          postgres    false    223            H           0    0    fournisseur_id_fournisseur_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.fournisseur_id_fournisseur_seq OWNED BY public.fournisseur.id_fournisseur;
          public          postgres    false    224            �            1259    32816    login_historique    TABLE     �   CREATE TABLE public.login_historique (
    id_login integer NOT NULL,
    id_utilisateur integer,
    nom_utilisateur character varying(255),
    role character varying(50),
    date_heure timestamp without time zone
);
 $   DROP TABLE public.login_historique;
       public         heap    postgres    false            �            1259    32819    login_historique_id_login_seq    SEQUENCE     �   CREATE SEQUENCE public.login_historique_id_login_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.login_historique_id_login_seq;
       public          postgres    false    225            I           0    0    login_historique_id_login_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.login_historique_id_login_seq OWNED BY public.login_historique.id_login;
          public          postgres    false    226            �            1259    32820 
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
       public         heap    postgres    false            �            1259    32826    medicament_id_medicament_seq    SEQUENCE     �   CREATE SEQUENCE public.medicament_id_medicament_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.medicament_id_medicament_seq;
       public          postgres    false    227            J           0    0    medicament_id_medicament_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.medicament_id_medicament_seq OWNED BY public.medicament.id_medicament;
          public          postgres    false    228            �            1259    32891    utilisateurs    TABLE       CREATE TABLE public.utilisateurs (
    id_utilisateur integer NOT NULL,
    nom_utilisateur character varying(255) NOT NULL,
    mot_de_passe character varying(255) NOT NULL,
    role character varying(50) NOT NULL,
    date_creation date DEFAULT CURRENT_DATE
);
     DROP TABLE public.utilisateurs;
       public         heap    postgres    false            �            1259    32890    utilisateurs_id_utilisateur_seq    SEQUENCE     �   CREATE SEQUENCE public.utilisateurs_id_utilisateur_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.utilisateurs_id_utilisateur_seq;
       public          postgres    false    230            K           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.utilisateurs_id_utilisateur_seq OWNED BY public.utilisateurs.id_utilisateur;
          public          postgres    false    229            t           2604    32833 &   approvisionnement id_approvisionnement    DEFAULT     �   ALTER TABLE ONLY public.approvisionnement ALTER COLUMN id_approvisionnement SET DEFAULT nextval('public.approvisionnement_id_approvisionnement_seq'::regclass);
 U   ALTER TABLE public.approvisionnement ALTER COLUMN id_approvisionnement DROP DEFAULT;
       public          postgres    false    216    215            w           2604    32834    client id_client    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN id_client SET DEFAULT nextval('public.client_id_client_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN id_client DROP DEFAULT;
       public          postgres    false    218    217            z           2604    32835    famille id_famille    DEFAULT     x   ALTER TABLE ONLY public.famille ALTER COLUMN id_famille SET DEFAULT nextval('public.famille_id_famille_seq'::regclass);
 A   ALTER TABLE public.famille ALTER COLUMN id_famille DROP DEFAULT;
       public          postgres    false    220    219            }           2604    32836    forme id_forme    DEFAULT     p   ALTER TABLE ONLY public.forme ALTER COLUMN id_forme SET DEFAULT nextval('public.forme_id_forme_seq'::regclass);
 =   ALTER TABLE public.forme ALTER COLUMN id_forme DROP DEFAULT;
       public          postgres    false    222    221            �           2604    32837    fournisseur id_fournisseur    DEFAULT     �   ALTER TABLE ONLY public.fournisseur ALTER COLUMN id_fournisseur SET DEFAULT nextval('public.fournisseur_id_fournisseur_seq'::regclass);
 I   ALTER TABLE public.fournisseur ALTER COLUMN id_fournisseur DROP DEFAULT;
       public          postgres    false    224    223            �           2604    32838    login_historique id_login    DEFAULT     �   ALTER TABLE ONLY public.login_historique ALTER COLUMN id_login SET DEFAULT nextval('public.login_historique_id_login_seq'::regclass);
 H   ALTER TABLE public.login_historique ALTER COLUMN id_login DROP DEFAULT;
       public          postgres    false    226    225            �           2604    32839    medicament id_medicament    DEFAULT     �   ALTER TABLE ONLY public.medicament ALTER COLUMN id_medicament SET DEFAULT nextval('public.medicament_id_medicament_seq'::regclass);
 G   ALTER TABLE public.medicament ALTER COLUMN id_medicament DROP DEFAULT;
       public          postgres    false    228    227            �           2604    32894    utilisateurs id_utilisateur    DEFAULT     �   ALTER TABLE ONLY public.utilisateurs ALTER COLUMN id_utilisateur SET DEFAULT nextval('public.utilisateurs_id_utilisateur_seq'::regclass);
 J   ALTER TABLE public.utilisateurs ALTER COLUMN id_utilisateur DROP DEFAULT;
       public          postgres    false    230    229    230            .          0    32784    approvisionnement 
   TABLE DATA           �   COPY public.approvisionnement (id_approvisionnement, id_medicament, id_fournisseur, quantite_commandee, date_approvisionnement, statut, prix_fournisseur, quantite_recue, commentaire) FROM stdin;
    public          postgres    false    215   �X       0          0    32790    client 
   TABLE DATA           �   COPY public.client (id_client, nom_client, prenom_client, date_naissance_client, adresse_client, telephone_client, date_creation, statut) FROM stdin;
    public          postgres    false    217   Y       2          0    32796    famille 
   TABLE DATA           Q   COPY public.famille (id_famille, nom_famille, statut, date_creation) FROM stdin;
    public          postgres    false    219   5Z       4          0    32802    forme 
   TABLE DATA           K   COPY public.forme (id_forme, nom_forme, statut, date_creation) FROM stdin;
    public          postgres    false    221   �[       6          0    32808    fournisseur 
   TABLE DATA           �   COPY public.fournisseur (id_fournisseur, nom_fournisseur, email_fournisseur, tel_fournisseur, adresse_fournisseur, statut, date_creation) FROM stdin;
    public          postgres    false    223   �\       8          0    32816    login_historique 
   TABLE DATA           g   COPY public.login_historique (id_login, id_utilisateur, nom_utilisateur, role, date_heure) FROM stdin;
    public          postgres    false    225   t^       :          0    32820 
   medicament 
   TABLE DATA           �   COPY public.medicament (id_medicament, nom_medicament, description_medicament, id_fournisseur, id_famille, id_forme, statut, quantite_medicament, prix_vente, prix_fournisseur) FROM stdin;
    public          postgres    false    227   �^       =          0    32891    utilisateurs 
   TABLE DATA           j   COPY public.utilisateurs (id_utilisateur, nom_utilisateur, mot_de_passe, role, date_creation) FROM stdin;
    public          postgres    false    230   p`       L           0    0 *   approvisionnement_id_approvisionnement_seq    SEQUENCE SET     Y   SELECT pg_catalog.setval('public.approvisionnement_id_approvisionnement_seq', 12, true);
          public          postgres    false    216            M           0    0    client_id_client_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.client_id_client_seq', 7, true);
          public          postgres    false    218            N           0    0    famille_id_famille_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.famille_id_famille_seq', 27, true);
          public          postgres    false    220            O           0    0    forme_id_forme_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.forme_id_forme_seq', 12, true);
          public          postgres    false    222            P           0    0    fournisseur_id_fournisseur_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.fournisseur_id_fournisseur_seq', 10, true);
          public          postgres    false    224            Q           0    0    login_historique_id_login_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.login_historique_id_login_seq', 89, true);
          public          postgres    false    226            R           0    0    medicament_id_medicament_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.medicament_id_medicament_seq', 11, true);
          public          postgres    false    228            S           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.utilisateurs_id_utilisateur_seq', 9, true);
          public          postgres    false    229            �           2606    32842 (   approvisionnement approvisionnement_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.approvisionnement
    ADD CONSTRAINT approvisionnement_pkey PRIMARY KEY (id_approvisionnement);
 R   ALTER TABLE ONLY public.approvisionnement DROP CONSTRAINT approvisionnement_pkey;
       public            postgres    false    215            �           2606    32844    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id_client);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    217            �           2606    32846    famille famille_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.famille
    ADD CONSTRAINT famille_pkey PRIMARY KEY (id_famille);
 >   ALTER TABLE ONLY public.famille DROP CONSTRAINT famille_pkey;
       public            postgres    false    219            �           2606    32848    forme forme_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.forme
    ADD CONSTRAINT forme_pkey PRIMARY KEY (id_forme);
 :   ALTER TABLE ONLY public.forme DROP CONSTRAINT forme_pkey;
       public            postgres    false    221            �           2606    32850    fournisseur fournisseur_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.fournisseur
    ADD CONSTRAINT fournisseur_pkey PRIMARY KEY (id_fournisseur);
 F   ALTER TABLE ONLY public.fournisseur DROP CONSTRAINT fournisseur_pkey;
       public            postgres    false    223            �           2606    32852 4   login_historique login_historique_id_utilisateur_key 
   CONSTRAINT     y   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_id_utilisateur_key UNIQUE (id_utilisateur);
 ^   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_id_utilisateur_key;
       public            postgres    false    225            �           2606    32854 &   login_historique login_historique_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_pkey PRIMARY KEY (id_login);
 P   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_pkey;
       public            postgres    false    225            �           2606    32856    medicament medicament_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_pkey PRIMARY KEY (id_medicament);
 D   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_pkey;
       public            postgres    false    227            �           2606    32899    utilisateurs utilisateurs_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id_utilisateur);
 H   ALTER TABLE ONLY public.utilisateurs DROP CONSTRAINT utilisateurs_pkey;
       public            postgres    false    230            �           2606    32860 7   approvisionnement approvisionnement_id_fournisseur_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.approvisionnement
    ADD CONSTRAINT approvisionnement_id_fournisseur_fkey FOREIGN KEY (id_fournisseur) REFERENCES public.fournisseur(id_fournisseur);
 a   ALTER TABLE ONLY public.approvisionnement DROP CONSTRAINT approvisionnement_id_fournisseur_fkey;
       public          postgres    false    223    215    4753            �           2606    32865 6   approvisionnement approvisionnement_id_medicament_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.approvisionnement
    ADD CONSTRAINT approvisionnement_id_medicament_fkey FOREIGN KEY (id_medicament) REFERENCES public.medicament(id_medicament);
 `   ALTER TABLE ONLY public.approvisionnement DROP CONSTRAINT approvisionnement_id_medicament_fkey;
       public          postgres    false    4759    215    227            �           2606    32875 %   medicament medicament_id_famille_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_famille_fkey FOREIGN KEY (id_famille) REFERENCES public.famille(id_famille);
 O   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_famille_fkey;
       public          postgres    false    4749    227    219            �           2606    32880 #   medicament medicament_id_forme_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_forme_fkey FOREIGN KEY (id_forme) REFERENCES public.forme(id_forme);
 M   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_forme_fkey;
       public          postgres    false    227    4751    221            �           2606    32885 )   medicament medicament_id_fournisseur_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_fournisseur_fkey FOREIGN KEY (id_fournisseur) REFERENCES public.fournisseur(id_fournisseur);
 S   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_fournisseur_fkey;
       public          postgres    false    227    4753    223            .   c   x���4BCSN##]]#SΠ���K9c�@�711'���� ��� ���:CN 2�4B2�����@�� ��Ȕ����f�B����=... �@&       0     x�e�MN�0���> F�M�%!��T�-�)q[K���$�G�(=G.�CDA�fVO�<=C|�=��h�$� q����E�E�y�ot	R�25&���w�Bd-�o`��6s5���\�PG��D�O��%:}��4]l�ڵ�J���k[��=��-�BwZ�ܶ�#r);�G�a�G�C�{�|����/x����\�w���8땡\P���l�v���?6y��ǔ��*\)��kY��1������Y6�-�d��ߕ_�Z��e�5�v[�7�zm      2   �  x�}�Kn�0���S�*,��d�A�E#Nv�L%Z@�C����s�b9	��h����σ�p۶Vjn�&X:�Y�u�������>4���f2��@��ٕ�ۚ�D�Ϗ��1��-v��$s��Е� )e6d}�)n��T�GW���+�cK�OeL�+?�+����[]緯ổ������r)�X�� �߻0��?�C�V,
x8�q(��U������6`[�S��t^kxr�۟�̳
zJg����7��Sh�м;�-����U��ˆP�9�+x��R�}ρsi��y�rtI7�s6&��v�U�ೊ�v�ȓ�3�j=��� �6���M/M�`c`��35�R-���5C�`ϱl4�#w��j�&r���Bn���Q�@fo_ӦF�I���-�w�PS      4   �   x�m�M
�0����������Lch�̐����{a�Ջ��|��o�j�-��⛞f7z�?ޯ'��t��?e`�؁�\� P�&�T��p�������x�����T�	�h#aj=���G*�-�Ăh�%�q�@����D(�ܶJ�/19S;      6   �  x�m�͎�0F��S� 3#���i�.&J���q�3��eC4y��9�b�؈I!;|�w?(��F�k�R��<vp���P���y�RJe��Q�A&���Nv��pq��c����F������{��	ݤ�:׉�{'Z�섆�m��qQ�P���e(�ɳZy�	�J�pIG�V8/U��5��ZT���{�#H���(�2��(��"�MF/��Nu�3`R2b~�7��ߠd���{�brѨ	�9;BS�q��uv�.�I��#g�?fp�k�H�3���3Sr4͋r�t��^9c���H�!t/�������g��@P��̞:k�I[p{)�$񪴼���r�%����C�Ұ�g�)��5�����jyVܾ��e�l��:q�!��-+���w)��0��a��>�x��_�ȏ^��I�o-�L���u��O����A 8;m/�Ѿ�`y�U©������?�      8   V   x�=�;� �z�\ �E>g�!h�Q4��'vN=+��G;��s��<�z���jLU�d)�p�@�j���T�
�,(1����;(      :   �  x���Mj�0���S� m��/S(��B �n�=v���rInԬz_�c)4P��5}Oo�pxԭ�Pee��G�����V~��}�������[îC��S��t�"Z��S���֎J�?�=��>6�V���]ITt�0N���!����JD�[pN�v�V��Pv�lX�g����d�K�Þ�R���Ѫ<��VXa)�A��_��j&װ׍h��tbb������ga*ld�0�P���� �Z!R���D�����3��5�����yjl���"��4��ݍ�"��ψ���L:���$�*DK�um���jݏm9��G�3��Ua�G3���N9X����ʁ'���~L-�l���*��cׁ      =   P   x�3����L��)��y�FF&�&�F&\F�ٙ�`���Y\��Z�,k̙���	!�R�RRKQ��8KR�K��$��1z\\\ |-!2     