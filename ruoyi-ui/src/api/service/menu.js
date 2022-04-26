import request from '@/utils/request'

// 查询首页菜单列表
export function listMenu(query) {
  return request({
    url: '/service/menu/list',
    method: 'get',
    params: query
  })
}

// 查询首页菜单详细
export function getMenu(menuId) {
  return request({
    url: '/service/menu/' + menuId,
    method: 'get'
  })
}

// 新增首页菜单
export function addMenu(data) {
  return request({
    url: '/service/menu',
    method: 'post',
    data: data
  })
}

// 修改首页菜单
export function updateMenu(data) {
  return request({
    url: '/service/menu',
    method: 'put',
    data: data
  })
}

// 删除首页菜单
export function delMenu(menuId) {
  return request({
    url: '/service/menu/' + menuId,
    method: 'delete'
  })
}
