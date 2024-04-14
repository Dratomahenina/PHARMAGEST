PGDMP                       |         
   Pharmagest    16.2    16.2 C    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            @           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            A           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            B           1262    16628 
   Pharmagest    DATABASE        CREATE DATABASE "Pharmagest" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';
    DROP DATABASE "Pharmagest";
                postgres    false            �            1255    16712    update_login_historique()    FUNCTION     �  CREATE FUNCTION public.update_login_historique() RETURNS trigger
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
       public          postgres    false            �            1259    16814    approvisionnement    TABLE       CREATE TABLE public.approvisionnement (
    id_approvisionnement integer NOT NULL,
    id_medicament integer,
    quantite_commandee integer NOT NULL,
    date_approvisionnement date DEFAULT CURRENT_DATE,
    statut character varying(20) DEFAULT 'en attente'::character varying
);
 %   DROP TABLE public.approvisionnement;
       public         heap    postgres    false            �            1259    16813 *   approvisionnement_id_approvisionnement_seq    SEQUENCE     �   CREATE SEQUENCE public.approvisionnement_id_approvisionnement_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE public.approvisionnement_id_approvisionnement_seq;
       public          postgres    false    226            C           0    0 *   approvisionnement_id_approvisionnement_seq    SEQUENCE OWNED BY     y   ALTER SEQUENCE public.approvisionnement_id_approvisionnement_seq OWNED BY public.approvisionnement.id_approvisionnement;
          public          postgres    false    225            �            1259    16634    client    TABLE     �  CREATE TABLE public.client (
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
       public          postgres    false    215            D           0    0    client_id_client_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_id_client_seq OWNED BY public.client.id_client;
          public          postgres    false    216            �            1259    16677    famille    TABLE     �   CREATE TABLE public.famille (
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
       public          postgres    false    220            E           0    0    famille_id_famille_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.famille_id_famille_seq OWNED BY public.famille.id_famille;
          public          postgres    false    219            �            1259    16686    forme    TABLE     �   CREATE TABLE public.forme (
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
       public          postgres    false    222            F           0    0    forme_id_forme_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.forme_id_forme_seq OWNED BY public.forme.id_forme;
          public          postgres    false    221            �            1259    16852    fournisseur    TABLE     �  CREATE TABLE public.fournisseur (
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
       public          postgres    false    228            G           0    0    fournisseur_id_fournisseur_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.fournisseur_id_fournisseur_seq OWNED BY public.fournisseur.id_fournisseur;
          public          postgres    false    227            �            1259    16695    login_historique    TABLE     �   CREATE TABLE public.login_historique (
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
       public          postgres    false    224            H           0    0    login_historique_id_login_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.login_historique_id_login_seq OWNED BY public.login_historique.id_login;
          public          postgres    false    223            �            1259    16863 
   medicament    TABLE     +  CREATE TABLE public.medicament (
    id_medicament integer NOT NULL,
    nom_medicament character varying(255) NOT NULL,
    description_medicament text,
    id_fournisseur integer,
    id_famille integer,
    id_forme integer,
    statut character varying(20) DEFAULT 'actif'::character varying
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
       public          postgres    false    230            I           0    0    medicament_id_medicament_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.medicament_id_medicament_seq OWNED BY public.medicament.id_medicament;
          public          postgres    false    229            �            1259    16645    utilisateurs    TABLE     �   CREATE TABLE public.utilisateurs (
    id_utilisateur integer NOT NULL,
    nom_utilisateur character varying(255) NOT NULL,
    mot_de_passe character varying(255) NOT NULL,
    role character varying(50) NOT NULL
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
       public          postgres    false    217            J           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.utilisateurs_id_utilisateur_seq OWNED BY public.utilisateurs.id_utilisateur;
          public          postgres    false    218                       2604    16817 &   approvisionnement id_approvisionnement    DEFAULT     �   ALTER TABLE ONLY public.approvisionnement ALTER COLUMN id_approvisionnement SET DEFAULT nextval('public.approvisionnement_id_approvisionnement_seq'::regclass);
 U   ALTER TABLE public.approvisionnement ALTER COLUMN id_approvisionnement DROP DEFAULT;
       public          postgres    false    225    226    226            t           2604    16652    client id_client    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN id_client SET DEFAULT nextval('public.client_id_client_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN id_client DROP DEFAULT;
       public          postgres    false    216    215            x           2604    16680    famille id_famille    DEFAULT     x   ALTER TABLE ONLY public.famille ALTER COLUMN id_famille SET DEFAULT nextval('public.famille_id_famille_seq'::regclass);
 A   ALTER TABLE public.famille ALTER COLUMN id_famille DROP DEFAULT;
       public          postgres    false    220    219    220            {           2604    16689    forme id_forme    DEFAULT     p   ALTER TABLE ONLY public.forme ALTER COLUMN id_forme SET DEFAULT nextval('public.forme_id_forme_seq'::regclass);
 =   ALTER TABLE public.forme ALTER COLUMN id_forme DROP DEFAULT;
       public          postgres    false    222    221    222            �           2604    16855    fournisseur id_fournisseur    DEFAULT     �   ALTER TABLE ONLY public.fournisseur ALTER COLUMN id_fournisseur SET DEFAULT nextval('public.fournisseur_id_fournisseur_seq'::regclass);
 I   ALTER TABLE public.fournisseur ALTER COLUMN id_fournisseur DROP DEFAULT;
       public          postgres    false    227    228    228            ~           2604    16698    login_historique id_login    DEFAULT     �   ALTER TABLE ONLY public.login_historique ALTER COLUMN id_login SET DEFAULT nextval('public.login_historique_id_login_seq'::regclass);
 H   ALTER TABLE public.login_historique ALTER COLUMN id_login DROP DEFAULT;
       public          postgres    false    223    224    224            �           2604    16866    medicament id_medicament    DEFAULT     �   ALTER TABLE ONLY public.medicament ALTER COLUMN id_medicament SET DEFAULT nextval('public.medicament_id_medicament_seq'::regclass);
 G   ALTER TABLE public.medicament ALTER COLUMN id_medicament DROP DEFAULT;
       public          postgres    false    229    230    230            w           2604    16654    utilisateurs id_utilisateur    DEFAULT     �   ALTER TABLE ONLY public.utilisateurs ALTER COLUMN id_utilisateur SET DEFAULT nextval('public.utilisateurs_id_utilisateur_seq'::regclass);
 J   ALTER TABLE public.utilisateurs ALTER COLUMN id_utilisateur DROP DEFAULT;
       public          postgres    false    218    217            8          0    16814    approvisionnement 
   TABLE DATA           �   COPY public.approvisionnement (id_approvisionnement, id_medicament, quantite_commandee, date_approvisionnement, statut) FROM stdin;
    public          postgres    false    226   �V       -          0    16634    client 
   TABLE DATA           �   COPY public.client (id_client, nom_client, prenom_client, date_naissance_client, adresse_client, telephone_client, date_creation, statut) FROM stdin;
    public          postgres    false    215   W       2          0    16677    famille 
   TABLE DATA           Q   COPY public.famille (id_famille, nom_famille, statut, date_creation) FROM stdin;
    public          postgres    false    220   :X       4          0    16686    forme 
   TABLE DATA           K   COPY public.forme (id_forme, nom_forme, statut, date_creation) FROM stdin;
    public          postgres    false    222   �Y       :          0    16852    fournisseur 
   TABLE DATA           �   COPY public.fournisseur (id_fournisseur, nom_fournisseur, email_fournisseur, tel_fournisseur, adresse_fournisseur, statut, date_creation) FROM stdin;
    public          postgres    false    228   �Z       6          0    16695    login_historique 
   TABLE DATA           g   COPY public.login_historique (id_login, id_utilisateur, nom_utilisateur, role, date_heure) FROM stdin;
    public          postgres    false    224   z\       <          0    16863 
   medicament 
   TABLE DATA           �   COPY public.medicament (id_medicament, nom_medicament, description_medicament, id_fournisseur, id_famille, id_forme, statut) FROM stdin;
    public          postgres    false    230   �\       /          0    16645    utilisateurs 
   TABLE DATA           [   COPY public.utilisateurs (id_utilisateur, nom_utilisateur, mot_de_passe, role) FROM stdin;
    public          postgres    false    217   *^       K           0    0 *   approvisionnement_id_approvisionnement_seq    SEQUENCE SET     X   SELECT pg_catalog.setval('public.approvisionnement_id_approvisionnement_seq', 2, true);
          public          postgres    false    225            L           0    0    client_id_client_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.client_id_client_seq', 7, true);
          public          postgres    false    216            M           0    0    famille_id_famille_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.famille_id_famille_seq', 27, true);
          public          postgres    false    219            N           0    0    forme_id_forme_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.forme_id_forme_seq', 12, true);
          public          postgres    false    221            O           0    0    fournisseur_id_fournisseur_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.fournisseur_id_fournisseur_seq', 10, true);
          public          postgres    false    227            P           0    0    login_historique_id_login_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.login_historique_id_login_seq', 32, true);
          public          postgres    false    223            Q           0    0    medicament_id_medicament_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.medicament_id_medicament_seq', 11, true);
          public          postgres    false    229            R           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.utilisateurs_id_utilisateur_seq', 44, true);
          public          postgres    false    218            �           2606    16821 (   approvisionnement approvisionnement_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.approvisionnement
    ADD CONSTRAINT approvisionnement_pkey PRIMARY KEY (id_approvisionnement);
 R   ALTER TABLE ONLY public.approvisionnement DROP CONSTRAINT approvisionnement_pkey;
       public            postgres    false    226            �           2606    16658    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id_client);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    215            �           2606    16684    famille famille_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.famille
    ADD CONSTRAINT famille_pkey PRIMARY KEY (id_famille);
 >   ALTER TABLE ONLY public.famille DROP CONSTRAINT famille_pkey;
       public            postgres    false    220            �           2606    16693    forme forme_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.forme
    ADD CONSTRAINT forme_pkey PRIMARY KEY (id_forme);
 :   ALTER TABLE ONLY public.forme DROP CONSTRAINT forme_pkey;
       public            postgres    false    222            �           2606    16861    fournisseur fournisseur_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.fournisseur
    ADD CONSTRAINT fournisseur_pkey PRIMARY KEY (id_fournisseur);
 F   ALTER TABLE ONLY public.fournisseur DROP CONSTRAINT fournisseur_pkey;
       public            postgres    false    228            �           2606    16720 4   login_historique login_historique_id_utilisateur_key 
   CONSTRAINT     y   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_id_utilisateur_key UNIQUE (id_utilisateur);
 ^   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_id_utilisateur_key;
       public            postgres    false    224            �           2606    16700 &   login_historique login_historique_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_pkey PRIMARY KEY (id_login);
 P   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_pkey;
       public            postgres    false    224            �           2606    16871    medicament medicament_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_pkey PRIMARY KEY (id_medicament);
 D   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_pkey;
       public            postgres    false    230            �           2606    16662    utilisateurs utilisateurs_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id_utilisateur);
 H   ALTER TABLE ONLY public.utilisateurs DROP CONSTRAINT utilisateurs_pkey;
       public            postgres    false    217            �           2620    16714 $   utilisateurs update_login_historique    TRIGGER     �   CREATE TRIGGER update_login_historique AFTER INSERT OR UPDATE ON public.utilisateurs FOR EACH ROW EXECUTE FUNCTION public.update_login_historique();
 =   DROP TRIGGER update_login_historique ON public.utilisateurs;
       public          postgres    false    231    217            �           2606    16701 5   login_historique login_historique_id_utilisateur_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.login_historique
    ADD CONSTRAINT login_historique_id_utilisateur_fkey FOREIGN KEY (id_utilisateur) REFERENCES public.utilisateurs(id_utilisateur);
 _   ALTER TABLE ONLY public.login_historique DROP CONSTRAINT login_historique_id_utilisateur_fkey;
       public          postgres    false    217    4746    224            �           2606    16877 %   medicament medicament_id_famille_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_famille_fkey FOREIGN KEY (id_famille) REFERENCES public.famille(id_famille);
 O   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_famille_fkey;
       public          postgres    false    4748    220    230            �           2606    16882 #   medicament medicament_id_forme_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_forme_fkey FOREIGN KEY (id_forme) REFERENCES public.forme(id_forme);
 M   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_forme_fkey;
       public          postgres    false    230    4750    222            �           2606    16872 )   medicament medicament_id_fournisseur_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicament
    ADD CONSTRAINT medicament_id_fournisseur_fkey FOREIGN KEY (id_fournisseur) REFERENCES public.fournisseur(id_fournisseur);
 S   ALTER TABLE ONLY public.medicament DROP CONSTRAINT medicament_id_fournisseur_fkey;
       public          postgres    false    4758    230    228            8   /   x�3�46�42 "#]]Cc��<�Ē�Լ�T.#Nܲ1z\\\ �~�      -     x�m�AN�0���> F��N�%!��T�-����`Gv�#p��#�!�T*�Y}����.�����k�(8�8X�X��6��&��0�B���5 C9ǹ�޻]�Y��7��Q���QVQ� %1��?b"���%:}��4�l:۵�J%�k[���-lBwX�ܶ�#b);�G3l��dc���S�!N�����˵/�ҽ�v6�Y�4�H�M�����f���{u&z���[�#
!����_l���p�;��g[g����~w~uch������o�EQ|�|      2   �  x�}�Kn�0���S�*,��d�A�E#Nv�L%Z@�C����s�b9	��h����σ�p۶Vjn�&X:�Y�u�������>4���f2��@��ٕ�ۚ�D�Ϗ��1��-v��$s��Е� )e6d}�)n��T�GW���+�cK�OeL�+?�+����[]緯ổ������r)�X�� �߻0��?�C�V,
x8�q(��U������6`[�S��t^kxr�۟�̳
zJg����7��Sh�м;�-����U��ˆP�9�+x��R�}ρsi��y�rtI7�s6&��v�U�ೊ�v�ȓ�3�j=��� �6���M/M�`c`��35�R-���5C�`ϱl4�#w��j�&r���Bn���Q�@fo_ӦF�I���-�w�PS      4   �   x�m�M
�0����������Lch�̐����{a�Ջ��|��o�j�-��⛞f7z�?ޯ'��t��?e`�؁�\� P�&�T��p�������x�����T�	�h#aj=���G*�-�Ăh�%�q�@����D(�ܶJ�/19S;      :   �  x�m�K�� ���>��0��n��Ť��jV�P�i��C`G�5��ŊyR;;�d~� ���,��a,w�Z�MX�}h����j�p�y�q�m/�N�Lr5���, �Yìp��"����Cr���-��~4�?������s$�UI�c0ɞ�\%Ȏ��_�q��z���Z��'-/�.��^��L�ߒ�6��q�R��f�z����.�����P>���:.����$(jYӏ��ɰ2~I�eYUuH5�����B^���	���A���H��7X��������
���v9'9��8{���G�gM���X�r�X=��w`�x��^Xm<���􃩁��A\��L�Y�o�����ٓ4:*)bn�R%�B�k�z?/���U�$9�,�?k�q6~�u
~��Q��ʬ����e�l��Xv�(H�W�)+�I�w���x���\��0\��&&��IWx�;�ԭ`~=l6�|8?�      6   U   x�E�1�  ��}��Rx�A�F�D��'o��z�9u�=��b���*�$�\0���6���J��(�"�_�f(      <   ;  x����J�0���O�P��v.W����fh��@��$��7r�|����nVD)���;����n�V�c#�6A�v�2�TB�h���A�B��qT��h�{������8��+-��׌�X9���⼜6=�ƞ�yԝ��Ss0y�
zb�v?A\�����AW��ɰ�x%=ʛ����޺�I�v���t��&��w/TBYF�6�T�降�N`�V�x��xI��-h�=d� o�5��5��紈䒶�e-1��؝���,#��v���:]���
xXEEvMwM#�5���z��0��M�t����	x�J������      /   9   x�3�L�KɄe�y)��E\F�9���"91��83��ː3;3�Sr3�b���� r�     