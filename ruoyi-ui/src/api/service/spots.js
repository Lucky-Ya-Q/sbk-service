import request from '@/utils/request'

// 查询旅游景点列表
export function listSpots(query) {
  return request({
    url: '/service/spots/list',
    method: 'get',
    params: query
  })
}

// 查询旅游景点详细
export function getSpots(spotsId) {
  return request({
    url: '/service/spots/' + spotsId,
    method: 'get'
  })
}

// 新增旅游景点
export function addSpots(data) {
  return request({
    url: '/service/spots',
    method: 'post',
    data: data
  })
}

// 修改旅游景点
export function updateSpots(data) {
  return request({
    url: '/service/spots',
    method: 'put',
    data: data
  })
}

// 删除旅游景点
export function delSpots(spotsId) {
  return request({
    url: '/service/spots/' + spotsId,
    method: 'delete'
  })
}
