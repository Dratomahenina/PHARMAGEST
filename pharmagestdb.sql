PGDMP             	            |         
   pharmagest    15.2    15.2 "               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    65538 
   pharmagest    DATABASE     }   CREATE DATABASE pharmagest WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';
    DROP DATABASE pharmagest;
                postgres    false            �            1255    73747    log_user_login()    FUNCTION     c  CREATE FUNCTION public.log_user_login() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    RAISE NOTICE 'Insertion dans la table audit : nom_utilisateur=%, date_connexion=%', NEW.nom_utilisateur, CURRENT_TIMESTAMP;
    INSERT INTO audit (nom_utilisateur, date_connexion)
    VALUES (NEW.nom_utilisateur, CURRENT_TIMESTAMP);
    RETURN NEW;
END;
$$;
 '   DROP FUNCTION public.log_user_login();
       public          postgres    false            �            1259    73741    audit    TABLE     �   CREATE TABLE public.audit (
    id_audit integer NOT NULL,
    nom_utilisateur character varying(255) NOT NULL,
    date_connexion timestamp without time zone NOT NULL
);
    DROP TABLE public.audit;
       public         heap    postgres    false            �            1259    73740    audit_id_audit_seq    SEQUENCE     �   CREATE SEQUENCE public.audit_id_audit_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.audit_id_audit_seq;
       public          postgres    false    221                       0    0    audit_id_audit_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.audit_id_audit_seq OWNED BY public.audit.id_audit;
          public          postgres    false    220            �            1259    65756    client    TABLE     l  CREATE TABLE public.client (
    id_client integer NOT NULL,
    nom_client character varying(50) NOT NULL,
    prenom_client character varying(50) NOT NULL,
    date_naissance_client date,
    adresse_client character varying(200),
    telephone_client character varying(20),
    nom_medecin character varying(100),
    date_creation date DEFAULT CURRENT_DATE
);
    DROP TABLE public.client;
       public         heap    postgres    false            �            1259    65755    client_id_client_seq    SEQUENCE     �   CREATE SEQUENCE public.client_id_client_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.client_id_client_seq;
       public          postgres    false    217                        0    0    client_id_client_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_id_client_seq OWNED BY public.client.id_client;
          public          postgres    false    216            �            1259    73731    fournisseur    TABLE     �   CREATE TABLE public.fournisseur (
    id_fournisseur integer NOT NULL,
    nom character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    telephone character varying(20) NOT NULL,
    adresse character varying(255) NOT NULL
);
    DROP TABLE public.fournisseur;
       public         heap    postgres    false            �            1259    73730    fournisseurs_id_seq    SEQUENCE     �   CREATE SEQUENCE public.fournisseurs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.fournisseurs_id_seq;
       public          postgres    false    219            !           0    0    fournisseurs_id_seq    SEQUENCE OWNED BY     V   ALTER SEQUENCE public.fournisseurs_id_seq OWNED BY public.fournisseur.id_fournisseur;
          public          postgres    false    218            �            1259    65540    utilisateurs    TABLE     �   CREATE TABLE public.utilisateurs (
    id_utilisateur integer NOT NULL,
    nom_utilisateur character varying(255) NOT NULL,
    mot_de_passe character varying(255) NOT NULL,
    role character varying(50) NOT NULL
);
     DROP TABLE public.utilisateurs;
       public         heap    postgres    false            �            1259    65539    utilisateurs_id_utilisateur_seq    SEQUENCE     �   CREATE SEQUENCE public.utilisateurs_id_utilisateur_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.utilisateurs_id_utilisateur_seq;
       public          postgres    false    215            "           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.utilisateurs_id_utilisateur_seq OWNED BY public.utilisateurs.id_utilisateur;
          public          postgres    false    214            y           2604    73744    audit id_audit    DEFAULT     p   ALTER TABLE ONLY public.audit ALTER COLUMN id_audit SET DEFAULT nextval('public.audit_id_audit_seq'::regclass);
 =   ALTER TABLE public.audit ALTER COLUMN id_audit DROP DEFAULT;
       public          postgres    false    221    220    221            v           2604    65759    client id_client    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN id_client SET DEFAULT nextval('public.client_id_client_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN id_client DROP DEFAULT;
       public          postgres    false    216    217    217            x           2604    73734    fournisseur id_fournisseur    DEFAULT     }   ALTER TABLE ONLY public.fournisseur ALTER COLUMN id_fournisseur SET DEFAULT nextval('public.fournisseurs_id_seq'::regclass);
 I   ALTER TABLE public.fournisseur ALTER COLUMN id_fournisseur DROP DEFAULT;
       public          postgres    false    218    219    219            u           2604    65543    utilisateurs id_utilisateur    DEFAULT     �   ALTER TABLE ONLY public.utilisateurs ALTER COLUMN id_utilisateur SET DEFAULT nextval('public.utilisateurs_id_utilisateur_seq'::regclass);
 J   ALTER TABLE public.utilisateurs ALTER COLUMN id_utilisateur DROP DEFAULT;
       public          postgres    false    215    214    215                      0    73741    audit 
   TABLE DATA           J   COPY public.audit (id_audit, nom_utilisateur, date_connexion) FROM stdin;
    public          postgres    false    221   (                 0    65756    client 
   TABLE DATA           �   COPY public.client (id_client, nom_client, prenom_client, date_naissance_client, adresse_client, telephone_client, nom_medecin, date_creation) FROM stdin;
    public          postgres    false    217   �(                 0    73731    fournisseur 
   TABLE DATA           U   COPY public.fournisseur (id_fournisseur, nom, email, telephone, adresse) FROM stdin;
    public          postgres    false    219   *                 0    65540    utilisateurs 
   TABLE DATA           [   COPY public.utilisateurs (id_utilisateur, nom_utilisateur, mot_de_passe, role) FROM stdin;
    public          postgres    false    215   2*       #           0    0    audit_id_audit_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.audit_id_audit_seq', 7, true);
          public          postgres    false    220            $           0    0    client_id_client_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.client_id_client_seq', 7, true);
          public          postgres    false    216            %           0    0    fournisseurs_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.fournisseurs_id_seq', 1, false);
          public          postgres    false    218            &           0    0    utilisateurs_id_utilisateur_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.utilisateurs_id_utilisateur_seq', 10, true);
          public          postgres    false    214            �           2606    73746    audit audit_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.audit
    ADD CONSTRAINT audit_pkey PRIMARY KEY (id_audit);
 :   ALTER TABLE ONLY public.audit DROP CONSTRAINT audit_pkey;
       public            postgres    false    221            }           2606    65762    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id_client);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    217                       2606    73738    fournisseur fournisseurs_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.fournisseur
    ADD CONSTRAINT fournisseurs_pkey PRIMARY KEY (id_fournisseur);
 G   ALTER TABLE ONLY public.fournisseur DROP CONSTRAINT fournisseurs_pkey;
       public            postgres    false    219            {           2606    65547    utilisateurs utilisateurs_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id_utilisateur);
 H   ALTER TABLE ONLY public.utilisateurs DROP CONSTRAINT utilisateurs_pkey;
       public            postgres    false    215            �           2620    73748    utilisateurs trigger_user_login    TRIGGER     }   CREATE TRIGGER trigger_user_login AFTER INSERT ON public.utilisateurs FOR EACH ROW EXECUTE FUNCTION public.log_user_login();
 8   DROP TRIGGER trigger_user_login ON public.utilisateurs;
       public          postgres    false    215    222               �   x�m�K
�0��u�^�af2�4g���`����K5�/?�)��r9�:�״ߏg`d0l'�B\DbRs�ü����j�=����?P��2���i��_a1&qR�M?-�	Xg� i$"�ޛ4��#�� oC5�         Q  x�M�Kn�0E�/��j�O�8ê��*�P�ub�R;��,�Kal�NR �s޽�����|he�VR`�1c@�Q�Z�F��2?O��& ���(eK�@k�;c��R�����֐FF'fE0)1�r�zmGl@/'��|�m��z��.�dY��3:
�q��י�ÿtc������2LH�w�ѽ�5�#Z�d��V���S�8�s1�Wڻ��J��O�kOS��J����R"zUi��h�����_/wǴ
2u�Vq.0y�m�2�_`N����?M�3�e�~d��BΙl\�ڮQÄPL	���UM0���2��(����v��*�ZdY�]��I            x������ � �         R   x�3����L��)��y\F�ٙ�`���Y\��Z�e̙���	!2��2�r3�(J�2��l����f͖��p����� E�>�     