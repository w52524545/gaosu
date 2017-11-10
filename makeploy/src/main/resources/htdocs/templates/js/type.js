/*
 *	全国三级城市联动 js版
 */
function Dsy(){
    this.Items = {};
}
Dsy.prototype.add = function(id,iArray){
    this.Items[id] = iArray;
}
Dsy.prototype.Exists = function(id){
    if(typeof(this.Items[id]) == "undefined") return false;
    return true;
}

function change(v){
    var str="0";
    for(i=0;i<v;i++){
        str+=("_"+(document.getElementById(s[i]).selectedIndex-1));
    };
    var ss=document.getElementById(s[v]);
    with(ss){
        length = 0;
        options[0]=new Option(opt0[v],opt0[v]);
        if(v && document.getElementById(s[v-1]).selectedIndex>0 || !v){
            if(dsy.Exists(str)){
                ar = dsy.Items[str];
                for(i=0;i<ar.length;i++){
                    options[length]=new Option(ar[i],ar[i]);
                }//end for
                if(v){ options[0].selected = true; }
            }
        }//end if v
        if(++v<s.length){change(v);}
    }//End with
}
var dsy = new Dsy();

dsy.add("0",["家居日用","食品","服装","数码","更多","礼品专区"]);
dsy.add("0_0",["日用品","家居","护肤品","家纺"]);
dsy.add("0_1",["地方特产","休闲零食","进口商品","酒水饮料","粮油副食","其他"]);
dsy.add("0_2",["童装","男装","女装","其他"]);
dsy.add("0_3",["数码配件"]);
dsy.add("0_4",["其他"]);
dsy.add("0_5",["礼品专区"]);
var s=["s_fatherType","s_childType"];//三个select的name
var opt0 = ["大类","子类"];//初始值

function init_area(){  //初始化函数
    for(i=0;i<s.length-1;i++){
        document.getElementById(s[i]).onchange=new Function("change("+(i+1)+")");
    }
    change(0);
}
/* 代码整理：懒人之家 www.lanrenzhijia.com */