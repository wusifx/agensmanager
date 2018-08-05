--liquibase formatted sql

--changeset "huang.chao":"20180805.2.create_vlabel_hc"
set graph_path=agens;
create vlabel hc;
--rollback drop vlabel hc;

--changeset "huang.chao":"20180805.2.drop_vlabel_hc"
set graph_path=agens;
drop vlabel hc;