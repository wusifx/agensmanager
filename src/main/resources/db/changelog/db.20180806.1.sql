--liquibase formatted sql

--changeset huang.chao:20180806.1.create_elabel_elink
set graph_path=agens;
create elabel if not exists elink;
--rollback drop elabel elink;
