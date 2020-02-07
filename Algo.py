// Algorithm for finding shortest path

from Graph import *
from PriorityQueue import *
from HeuristicInfo import *
import copy

initPriorityQueue()
def isExist(_list,el):
    return (el in _list)

def AStar(graphInp, heuristicInp):
    # konstruktor graph
    initGraph(len(graphInp))

    # konstrukto priorQueue
    initPriorityQueue()

    # isi data graph dengan graph dari input
    for i in range(len(graphInp)):
        for j in range(len(graphInp)):
            addEdgeGraph(i,j,graphInp[i][j])

    # isi data heuristic info dengan heuristic info dari input
    for i in range(len(heuristicInp)):
        addHeuristicInfo(i,heuristicInp[i])

    start = 0
    last  = len(graphInp) - 1
    i = start
    solution = []
    solution.append(start)

    while(not(isExist(solution,last))):
        candidate = copy.deepcopy(graph['storage'][i])
        #print(candidate)
        for j in range(len(candidate)):
            if(graph['storage'][i][j] > 0):
                # masukkan ke priorQueue
                add([graph['sum'][i] + graph['storage'][i][j] + HeuristicInfo[j], j])

                # masukkan ke graph
                graph['sum'][j] = graph['sum'][i] + graph['storage'][i][j]

        nextNode = delete()

        while graph['storage'][solution[len(solution) - 1]][nextNode[1]] <= 0:
            solution = solution [:-1]

        solution.append(nextNode[1])
        i = nextNode[1]

    return solution
