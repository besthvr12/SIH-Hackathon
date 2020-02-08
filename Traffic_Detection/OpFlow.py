import cv2 as cv
import numpy as np
import os
from statistics import mean
import time
import json


cap = cv.VideoCapture(cv.samples.findFile("/home/siddhant/Datasets/SIH/Traffic_footage_1_low_res.mp4"))
ret, frame1 = cap.read()
prvs = cv.cvtColor(frame1,cv.COLOR_BGR2GRAY)
hsv = np.zeros_like(frame1)
hsv[...,1] = 255

current_path = os.getcwd()
f = open(f'{current_path}/record.csv', 'a')

start_time = time.time()
frame_count = 0

f.write("time,vel0,vel-pi/2,vel-pi,vel-3pi/2")

while(1):
    
    ret, frame2 = cap.read()
    next = cv.cvtColor(frame2,cv.COLOR_BGR2GRAY)
    flow = cv.calcOpticalFlowFarneback(prvs,next, None, 0.5, 3, 15, 3, 3, 1.2, 0)
    start = time.time()

    #print(np.shape(flow))
    
    #cv.imwrite(f'frame - {frame_count}.jpg', flow)
    mag, ang = cv.cartToPolar(flow[...,0], flow[...,1])
    hsv[...,0] = ang*180/np.pi/2
    hsv[...,2] = cv.normalize(mag,None,0,255,cv.NORM_MINMAX)
    bgr = cv.cvtColor(hsv,cv.COLOR_HSV2BGR)
    
    bgr = cv.medianBlur(bgr, 5)
    cv.imshow('frame2',bgr)
    
    
    k = cv.waitKey(30) & 0xff
    if k == 27:
        break
        cv.destroyAllWindows()
    elif k == ord('s'):
        cv.imwrite('opticalfb".png',frame2)
        cv.imwrite('opticalhsv.png',bgr)
        
    elapsed_time = time.time() - start_time
    #print(f"{}")
    #print(f"time elapsed - {elapsed_time}")
    frame_count += 1
    
    vel_arr = [[] for i in range(4)]
    pi = 3.14
    for i in range(0, 239, 5):
        for j in range(0, 419, 5):
            if (ang[i][j] > pi/4) and (ang[i][j] < pi/2 + pi/4):
                vel_arr[0].append(mag[i][j])
            if (ang[i][j] > pi/2 + pi/4) and (ang[i][j] < pi + pi/4):
                vel_arr[1].append(mag[i][j])
            if (ang[i][j] > pi + pi/4) and (ang[i][j] < 1.5*pi + pi/4):
                vel_arr[2].append(mag[i][j])
            if (((ang[i][j] > 1.5*pi + pi/4) and (ang[i][j] < 2*pi + pi/4)) or ((ang[i][j] > 0) and (ang[i][j] < pi/4))):
                vel_arr[3].append(mag[i][j])
    end = time.time() - start
    
    vel0 = mean(vel_arr[0])/end
    vel1 = mean(vel_arr[1])/end
    vel2 = mean(vel_arr[2])/end
    vel3 = mean(vel_arr[3])/end
    f.write(f"{end},")
    f.write(f"{vel0},{vel1},{vel2},{vel3}\n")
    
    prvs = next
f.close()
