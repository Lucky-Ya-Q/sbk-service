import request from '@/utils/request'

// 查询补卡上传记录列表
export function listBuka(query) {
  return request({
    url: '/service/buka/list',
    method: 'get',
    params: query
  })
}

// 查询补卡上传记录详细
export function getBuka(bukaId) {
  return request({
    url: '/service/buka/' + bukaId,
    method: 'get'
  })
}

// 新增补卡上传记录
export function addBuka(data) {
  return request({
    url: '/service/buka',
    method: 'post',
    data: data
  })
}

// 修改补卡上传记录
export function updateBuka(data) {
  return request({
    url: '/service/buka',
    method: 'put',
    data: data
  })
}

// 删除补卡上传记录
export function delBuka(bukaId) {
  return request({
    url: '/service/buka/' + bukaId,
    method: 'delete'
  })
}
