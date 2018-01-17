var tree_structure = {
    chart: {
        container: "#OrganiseChart6",
        levelSeparation:    20,
        siblingSeparation:  15,
        subTeeSeparation:   15,
        rootOrientation: "NORTH",

        node: {
            HTMLclass: "tennis-draw",
            drawLineThrough: false
        },
        connectors: {
            type: "step",
            style: {
                "stroke-width": 2,
                "stroke": "#ccc"
            }
        }
    },
    nodeStructure: {}
};
