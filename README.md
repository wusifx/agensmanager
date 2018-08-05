# agensmanager
agens graph manager

alias:superman
developerId:superman
securityCode:adbe6cf4-5bf8-4867-8824-56c86feeefc0



create vlabel
create elabel
create link rule（vlabelA-》elabel-》vlabelB）【通过vlabelA.propeity,link->weight】
insert vlabel
delete vlabel

///定义vlabel A与vlabel B的关联关系。即A的property1与B的property2进行关联，关联有方向性。
当进行添加时，自动创建关联。
当进行修改特定属性时，关联随之进行修改

使用内置功能 ，当某个顶点删除时，自动创建新的定点。（属性值是否允许修改，，，只允许删除后重建？）
///需要提供webhook机制

api只能定义边策略，和增加顶点