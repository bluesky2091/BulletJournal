import React from 'react';
import {Collapse, Empty, List, Timeline} from 'antd';
import {AccountBookOutlined, CarryOutOutlined, FileTextOutlined} from '@ant-design/icons';
import {ProjectItems} from '../../features/myBuJo/interface';
import NoteItem from './note-item.component';
import TaskItem from "./task-item.component";

const {Panel} = Collapse;

type ProjectModelItemsProps = {
    projectItems: ProjectItems[];
};

const getTasksPanel = (items: ProjectItems, index: number) => {
    if (items.tasks.length === 0) {
        return null;
    }
    return (
        <Panel
            header={items.dayOfWeek}
            key={`tasks${index}`}
            extra={<CarryOutOutlined/>}
        >
            <List>
                {items.tasks.map(item => {
                    return (<List.Item key={item.id}>
                        <TaskItem task={item} isComplete={false}/>
                    </List.Item>);
                })}
            </List>
        </Panel>
    );
};

const getTransactionsPanel = (items: ProjectItems, index: number) => {
    if (items.transactions.length === 0) {
        return null;
    }
    return (
        <Panel
            header={items.dayOfWeek}
            key={`transactions${index}`}
            extra={<AccountBookOutlined/>}
        >
        </Panel>
    );
};

const getNotesPanel = (items: ProjectItems, index: number) => {
    if (items.notes.length === 0) {
        return null;
    }
    return (
        <Panel
    header={items.dayOfWeek}
    key={`notes${index}`}
    extra={<FileTextOutlined/>}
    >
        <List>
        {items.notes.map(item=>{
            return (<List.Item key={item.id}>
                        <NoteItem note={item}/>
                    </List.Item>);
        })}
        </List>
    </Panel>
    );
};

const ProjectModelItems: React.FC<ProjectModelItemsProps> = props => {
    if (!props.projectItems || props.projectItems.length === 0) {
        return <Empty />;
    }
    return (
        <Timeline mode={'left'}>
            {props.projectItems.map((items, index) => {
                return (
                    <Timeline.Item
                        key={items.date}
                        label={items.date}
                        style={{marginLeft: '-65%'}}
                    >
                        <Collapse
                            defaultActiveKey={[
                                'tasks' + index,
                                'transactions' + index,
                                'notes' + index
                            ]}
                        >
                            {getTasksPanel(items, index)}
                            {getTransactionsPanel(items, index)}
                            {getNotesPanel(items, index)}
                        </Collapse>
                    </Timeline.Item>
                );
            })}
        </Timeline>
    );
};

export default ProjectModelItems;
