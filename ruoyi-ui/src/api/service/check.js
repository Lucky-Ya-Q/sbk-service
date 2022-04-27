import request from '@/utils/request'

// 查询检测上传记录列表
export function listCheck(query) {
  return request({
    url: '/service/check/list',
    method: 'get',
    params: query
  })
}

// 查询检测上传记录详细
export function getCheck(checkId) {
  return request({
    url: '/service/check/' + checkId,
    method: 'get'
  })
}

// 新增检测上传记录
export function addCheck(data) {
  return request({
    url: '/service/check',
    method: 'post',
    data: data
  })
}

// 修改检测上传记录
export function updateCheck(data) {
  return request({
    url: '/service/check',
    method: 'put',
    data: data
  })
}

// 删除检测上传记录
export function delCheck(checkId) {
  return request({
    url: '/service/check/' + checkId,
    method: 'delete'
  })
}
