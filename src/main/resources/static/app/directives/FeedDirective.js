var app = angular.module('ConceptMap');

app.directive('feedback', function() {
    return {
        restrict: 'E',
        template: '<div class="draw"></div>',
        replace: true,
        scope: {
            model: '=data'
        },
        link: function(scope, element, attrs) {

            var $ = go.GraphObject.make;
            var myDiagram =
                $(go.Diagram, element[0], {
                    initialContentAlignment: go.Spot.Center,
                    "toolManager.mouseWheelBehavior": go.ToolManager.WheelZoom,
                    "clickCreatingTool.archetypeNodeData": {
                        text: "Khái Niệm"
                    },
                    "undoManager.isEnabled": true
                });


            // define the Node template
            myDiagram.nodeTemplate =
                $(go.Node, "Auto",
                    new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                    // define the node's outer shape, which will surround the TextBlock
                    $(go.Shape, "RoundedRectangle",  {
                        parameter1: 20, // the corner has a large radius
                        fill: "white",
                        
                        portId: "",
                        fromLinkable: true,
                        toLinkable: true,

                        cursor: "pointer"
                    }, new go.Binding("stroke", "color")),
                    $(go.TextBlock, {
                            font: "bold 11pt helvetica, bold arial, sans-serif",
                            editable: true // editing the text automatically updates the model data
                        },
                        new go.Binding("text", "text").makeTwoWay(), new go.Binding("stroke", "color"))
                );

            

            myDiagram.linkTemplate =
                $(go.Link,

                    $(go.Shape, {
                        isPanelMain: true,
                        strokeWidth: 2
                    }, new go.Binding("stroke", "color")),
                    $(go.Shape, {
                        toArrow: "standard",
                        stroke: null
                    }),
                    $(go.Panel, "Auto",
                        $(go.Shape, {
                            fill: $(go.Brush, go.Brush.Radial, {
                                0: "white",
                                0.4: "white",
                                1: "rgba(240, 240, 240, 0)"
                            }),
                            stroke: null
                        }),
                        $(go.TextBlock, "Quan Hệ", {
                                textAlign: "center",
                                font: "10pt helvetica, arial, sans-serif",
                                stroke: "black",
                                margin: 4,
                                editable: true
                            },
                            new go.Binding("text", "text").makeTwoWay())
                    )
                );

            function updateAngular(e) {
                if (e.isTransactionFinished) scope.$apply();
            }
            scope.$watch("model", function(newmodel) {
                var oldmodel = myDiagram.model;

                if (oldmodel !== newmodel) {
                    if (oldmodel) oldmodel.removeChangedListener(updateAngular);
                    newmodel.addChangedListener(updateAngular);
                    myDiagram.model = newmodel;
                }
            });
            myDiagram.addDiagramListener("ChangedSelection", function(e) {
                $model = JSON.parse(myDiagram.model.toJSON());
                var selnode = myDiagram.selection.first();
                myDiagram.model.selectedNodeData = (selnode instanceof go.Node ? selnode.data : null);
                scope.$apply();
            });
        }
    };
});
