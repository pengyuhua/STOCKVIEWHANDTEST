// 全屏
export const fullscreen = function () {
  let elem = document.body;
  if (elem.webkitRequestFullScreen) {
    elem.webkitRequestFullScreen();
  } else if (elem.mozRequestFullScreen) {
    elem.mozRequestFullScreen();
  } else if (elem.requestFullScreen) {
    elem.requestFullscreen();
  } else {
    elem.alert('您的浏览器尚不支持全屏或全屏功能已被禁用')
  }
}

//随机数获取
export const getRandNum = function (Min, Max) {
  let Range = Max - Min;
  let Rand = Math.random();
  let num = Min + Math.round(Rand * Range); //四舍五入
  return num
}

//提示信息
export const showInfo = ( vm, type, content )=>{
  switch (type) {
    case 'info':
      vm.$Message.info(content)
      break;
    case 'warn':
      vm.$Message.warning(content)
      break;
    case 'success':
      vm.$Message.success(content)
      break;
    case 'fail':
      vm.$Message.error(content)
      break;
  }
}
