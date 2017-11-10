//轮播
$(function(){
  var swiper = new Swiper('.swiper', {
    pagination : '.pagination',
    loop:true,
    grabCursor: true
  });
    $('.pagination1 .swiper-pagination-switch').click(function(){
      swiper.swipeTo($(this).index())
  });
  });
