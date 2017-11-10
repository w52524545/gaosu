 $(document).ready(function(){
    $(function () {
        $(".sort_type li").click(function() {
            tabcon(this,".sort_type_cont");
        });
        function tabcon(op,con){
            var index = $(op).index();
            $(con).hide().eq(index).show();
            $(op).parent().find("li").removeClass("cur").eq(index).addClass("cur");
            $(op).parent().find("a").removeClass("cur").eq(index).addClass("cur");
            $(op).parent().find("i").removeClass("cur").eq(index).addClass("cur");
        }
    });
              $(".sort_total li").click(function() {
                 var index = $(this).index();
              $(this).parent().find("li").removeClass("cur").eq(index).addClass("cur");
              $(this).parent().find("i").removeClass("cur").eq(index).addClass("cur");
              $(".sort_type_cont").hide("slow");
        });
              $(".filter li").click(function() {
                   var index = $(this).index();
                   $(this).parent().find("a").removeClass("cur").eq(index).addClass("cur");
                  $(".sort_type_cont").hide();
              });
             
               $(".sort_imgmenu li").click(function() {
                   var index = $(this).index();
                   $(this).parent().find("img").removeClass("cur").eq(index).addClass("cur"); 
              });
       });