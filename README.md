Intelligent Traffic Light Simulation
This project is a simulation of an intelligent traffic light control system at a four-way intersection. The goal is to adjust traffic light cycles dynamically based on real-time traffic density on each approach (North, South, East, West).

Table of Contents

    • Features
    
    • Technology Stack
    
    • System Design
    
        ◦ Intersection Model
        
        ◦ Adaptive Algorithm
        
    • Input & Output Format
    
    • Getting Started
    
        ◦ Running the Simulation

Features

    • Realistic Intersection Representation: Four approach roads (North, South, East, West)
    
    • Adaptive Signal Control: Traffic light cycles respond to vehicle density on each road
    
    • Standard & Extended Phases: Green, Yellow, Red
    
    • Safety Assurance: No conflicting green signals across crossing directions
    
    • Vehicle Tracking: Counts waiting vehicles per approach
    
    • JSON Command Interface: Accepts a sequence of addVehicle and step commands
    
    • Customizable Algorithms: Proportional timing, waiting-time prioritization, etc.
    
    • Added strategy: Allows to explore simple tweaks to the algorithm.

Technology Stack
    
    • Language: Java (python for the analysis)
    
    • Build Tool: maven
    
    • JSON Parsing: Jackson for Java

System Design
Intersection Model
    
    • Four entry queues: north, south, east, west
    
    • Each queue holds vehicles waiting for the green phase
    
Adaptive Algorithm
    
    • Max-Cars Priority: Roads with most waiting vehicles get next green.  This allows for the most efficient clearance of the intersection.
    
Input & Output Format
Input JSON

{

  "commands": [
  
    { "type": "addVehicle", "vehicleId": "vehicle1", "startRoad": "south", "endRoad": "north" },
    { "type": "step" },
    { "type": "addVehicle", "vehicleId": "vehicle2", "startRoad": "north", "endRoad": "south" },
    { "type": "step" }
  ]
}


    • addVehicle: Adds a vehicle with vehicleId to startRoad, destined for endRoad.
    • step: Advances the simulation by one time unit; vehicles on the road with the green light pass through.
Output JSON

{
  "stepStatuses": [
  
    { "leftVehicles": ["vehicle1"] },
    { "leftVehicles": [] },
    { "leftVehicles": ["vehicle2"] },
    { "leftVehicles": [] }
  ]
}

    • stepStatuses: Array of results for each step command.
    • leftVehicles: List of vehicle IDs that left the intersection during that step.

Getting Started

Running the Simulation

Execute with one command, specifying input and output JSON files, for example:
java -jar target/traffic-sim-1.0-SNAPSHOT.jar input.json output.json


Prepared by Kamil Dalidowicz
