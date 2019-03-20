alter table t_lab_pcb_sq add  jgcs2 VARCHAR(30);
alter table t_lab_pcb_sq add  jgcs3 VARCHAR(30);
alter table t_lab_pcb_sq add  jgcs4 VARCHAR(30);
alter table t_lab_pcb_sq add  jgcs5 VARCHAR(30);
alter table t_lab_pcb_sq add    jhsj2 VARCHAR(20);
alter table t_lab_pcb_sq add    jhsj3 VARCHAR(20);
alter table t_lab_pcb_sq add    jhsj4 VARCHAR(20);
alter table t_lab_pcb_sq add    jhsj5 VARCHAR(20);
alter table t_lab_pcb_sq add    bj2 DOUBLE;
alter table t_lab_pcb_sq add    bj3 DOUBLE;
alter table t_lab_pcb_sq add    bj4 DOUBLE;
alter table t_lab_pcb_sq add    bj5 DOUBLE;
alter table t_lab_pcb_sq add    zbj DOUBLE;
alter table t_lab_pcb_sq add   jhsj12 varchar(20);
alter table t_lab_pcb_sq add   jhsj22 varchar(20);
alter table t_lab_pcb_sq add   jhsj32 varchar(20);
alter table t_lab_pcb_sq add   jhsj42 varchar(20);
alter table t_lab_pcb_sq add   jhsj52 varchar(20);

CREATE TABLE
    t_lab_all
    (
        id INT NOT NULL AUTO_INCREMENT,
        sqId INT NOT NULL,
        fileName VARCHAR(200),
        filePath VARCHAR(400),
        createTime VARCHAR(20),
        gyyq VARCHAR(40),
        ddbh VARCHAR(100),
        numSet INT,
        numUnit INT,
        cs VARCHAR(10),
        ccChang FLOAT(8,2),
        ccKuang FLOAT(8,2),
        cpbh VARCHAR(10),
        cl VARCHAR(20),
        tbNei VARCHAR(10),
        tbWai VARCHAR(10),
        zh VARCHAR(10),
        zhColor VARCHAR(10),
        zf VARCHAR(10),
        zfColor VARCHAR(10),
        cstd VARCHAR(2),
        cstdType VARCHAR(10),
        wxjgfs VARCHAR(20),
        jszyq VARCHAR(20),
        dcbg VARCHAR(2),
        zkcsbg VARCHAR(2),
        cpjcbg VARCHAR(2),
        fgzh VARCHAR(20),
        bmtc VARCHAR(20),
        bmtchd VARCHAR(20),
        kzms VARCHAR(20),
        weldType INT,
        num INT,
        piles INT,
        paster INT,
        paster2 INT,
        gyType INT,
        BGAType INT,
        hjgy INT,
        maxSize INT,
        ju FLOAT(8,2),
        xzzj FLOAT(8,2),
        numSet2 INT,
        ccChang2 FLOAT(8,2),
        size VARCHAR(10),
        cpbh2 VARCHAR(10),
        cl2 VARCHAR(20),
        bmtc2 VARCHAR(20),
        bmtchd2 VARCHAR(20),
        type VARCHAR(30),
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

